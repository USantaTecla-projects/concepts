import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { BehaviorSubject, Observable, shareReplay, take } from 'rxjs';
import { ConceptStore } from 'src/app/knowledge/data-access/concept.store';
import { Concept } from 'src/app/shared/types/concept/concept.model';
import { Page } from 'src/app/shared/types/misc/dto/page-response.dto';
import { State } from 'src/app/shared/types/misc/enum/state.enum';
import { KnowledgeDialogCreateConceptComponent } from '../../dialog/knowledge-dialog-concept-create/knowledge-dialog-concept-create.component';
import { KnowledgeDialogDeleteComponent } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';

@Component({
  selector: 'app-knowledge-concept-list',
  templateUrl: './knowledge-concept-list.component.html',
  styleUrls: ['./knowledge-concept-list.component.scss'],
})
export class KnowledgeConceptListComponent implements OnInit {
  @Input() conceptsPage!: Page<Concept> | null;

  @Input() totalElements: number | null = 0;

  @Input() state: string | null = State.INIT;

  @Output() selectConcept: EventEmitter<number> = new EventEmitter();

  @Output() createConcept: EventEmitter<Concept> = new EventEmitter();

  @Output() updateConcept: EventEmitter<Concept> = new EventEmitter();

  @Output() deleteConcept: EventEmitter<number> = new EventEmitter();

  @Output() getPage: EventEmitter<number> = new EventEmitter();

  selectedConceptID!: number;

  numberOfConceptsSubejct: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  numberOfConcepts$!: Observable<number>;

  constructor(public dialog: MatDialog, public concepStore: ConceptStore) {}

  ngOnInit(): void {
    this.numberOfConcepts$ = this.numberOfConceptsSubejct.asObservable();

    this.concepStore
      .countConcepts()
      .pipe(take(1), shareReplay())
      .subscribe(count => this.numberOfConceptsSubejct.next(count));
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
        this.updateNumberOfConcepts(1);
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

  updateNumberOfConcepts(adder: number) {
    this.numberOfConceptsSubejct.next(this.numberOfConceptsSubejct.value + adder);
  }
}
