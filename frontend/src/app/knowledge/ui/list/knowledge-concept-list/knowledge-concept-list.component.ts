import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AnswerStore } from 'src/app/knowledge/data-access/answer.store';
import { Concept, ConceptStore } from 'src/app/knowledge/data-access/concept.store';
import { JustificationStore } from 'src/app/knowledge/data-access/justification.store';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { KnowledgeDialogCreateConceptComponent } from '../../dialog/knowledge-dialog-concept-create/knowledge-dialog-concept-create.component';

@Component({
  selector: 'app-knowledge-concept-list',
  templateUrl: './knowledge-concept-list.component.html',
  styleUrls: ['./knowledge-concept-list.component.scss'],
})
export class KnowledgeConceptListComponent implements OnInit {
  @Input() concepts: Concept[] | null = [];

  state: string = State.INIT;

  constructor(
    public dialog: MatDialog,
    private snackbarService: SnackbarService,
    private conceptStore: ConceptStore,
    private answerStore: AnswerStore,
    private justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {
    this.conceptStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  selectConcept(conceptID: number) {
    this.answerStore.setConceptID(conceptID);
    this.justificationStore.removeAnswerID();
  }

  createConcept(conceptFormValue: any) {
    this.conceptStore.createConcept(conceptFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept created.'),
      error: () => this.snackbarService.openSnackBar('Error creating concept.'),
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(KnowledgeDialogCreateConceptComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'concept',
      },
    });

    dialogRef.afterClosed().subscribe(conceptFormValue => {
      if (conceptFormValue) this.createConcept(conceptFormValue);
    });
  }
}
