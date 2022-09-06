import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConceptStore } from 'src/app/knowledge/data-access/concept.store';
import { Concept } from 'src/app/knowledge/data-access/model/concept.model';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { KnowledgeDialogCreateConceptComponent } from '../../dialog/knowledge-dialog-concept-create/knowledge-dialog-concept-create.component';
import { KnowledgeDialogDeleteComponent } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';

@Component({
  selector: 'app-knowledge-concept-list',
  templateUrl: './knowledge-concept-list.component.html',
  styleUrls: ['./knowledge-concept-list.component.scss'],
})
export class KnowledgeConceptListComponent implements OnInit {
  @Input() concepts: Concept[] | null = [];

  @Output() selectConcept: EventEmitter<number> = new EventEmitter();

  @Output() createConcept: EventEmitter<Concept> = new EventEmitter();

  @Output() updateConcept: EventEmitter<Concept> = new EventEmitter();

  @Output() deleteConcept: EventEmitter<number> = new EventEmitter();

  state: string = State.INIT;

  selectedConceptID!: number;

  constructor(private conceptStore: ConceptStore, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.conceptStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

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
      if (deleteConcept) this.deleteConcept.emit(this.selectedConceptID);
    });
  }
}
