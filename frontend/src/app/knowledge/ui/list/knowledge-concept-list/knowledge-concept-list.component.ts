import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { Concept } from 'src/app/knowledge/types/concept.model';
import { State } from 'src/app/shared/interfaces/enums/state.enum';
import { Page } from 'src/app/shared/interfaces/page-response.dto';
import { KnowledgeDialogCreateConceptComponent } from '../../dialog/knowledge-dialog-concept-create/knowledge-dialog-concept-create.component';
import { KnowledgeDialogDeleteComponent } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';

@Component({
  selector: 'app-knowledge-concept-list',
  templateUrl: './knowledge-concept-list.component.html',
  styleUrls: ['./knowledge-concept-list.component.scss'],
})
export class KnowledgeConceptListComponent {
  @Input() conceptsPage!: Page<Concept> | null;

  @Input() totalElements: number | null = 0;

  @Input() state: string | null = State.INIT;

  @Output() selectConcept: EventEmitter<number> = new EventEmitter();

  @Output() createConcept: EventEmitter<Concept> = new EventEmitter();

  @Output() updateConcept: EventEmitter<Concept> = new EventEmitter();

  @Output() deleteConcept: EventEmitter<number> = new EventEmitter();

  @Output() getPage: EventEmitter<number> = new EventEmitter();

  selectedConceptID!: number;

  constructor(public dialog: MatDialog) {}

  onConceptSelect(conceptID: number): void {
    this.selectedConceptID = conceptID;
    this.selectConcept.emit(this.selectedConceptID);
  }

  onConceptCreate(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogCreateConceptComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'concept',
      },
    });

    dialogRef.afterClosed().subscribe(conceptFormValue => {
      if (conceptFormValue) {
        const newConcept: Concept = { ...conceptFormValue };
        this.createConcept.emit(newConcept);
      }
    });
  }

  onConceptUpdate(updatedConcept: Concept) {
    this.updateConcept.emit(updatedConcept);
  }

  onConceptDelete(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogDeleteComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'concept',
      },
    });

    dialogRef.afterClosed().subscribe(deleteConcept => {
      if (deleteConcept) {
        this.deleteConcept.emit(this.selectedConceptID);
        this.selectedConceptID = 0;
      }
    });
  }

  onPageGet(page: PageEvent) {
    const { pageIndex } = { ...page };
    this.getPage.emit(pageIndex);
  }
}
