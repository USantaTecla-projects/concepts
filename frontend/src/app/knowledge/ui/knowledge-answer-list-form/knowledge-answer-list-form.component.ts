import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActionType } from 'src/app/shared/utils/enums/action-type.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { Answer, AnswerStore } from '../../data-access/answer.store';
import { JustificationStore } from '../../data-access/justification.store';
import { KnowledgeDeleteDialogComponent } from '../knowledge-delete-dialog/knowledge-delete-dialog.component';

@Component({
  selector: 'app-knowledge-answer-list-form',
  templateUrl: './knowledge-answer-list-form.component.html',
  styleUrls: ['./knowledge-answer-list-form.component.scss'],
})
export class KnowledgeAnswerListFormComponent implements OnInit {
  @Input() answer!: Answer;

  answerForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private snackbarService: SnackbarService,
    public dialog: MatDialog,
    private answerStore: AnswerStore,
    private justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {
    this.answerForm = this.formBuilder.group({
      text: [this.answer.text, [Validators.required]],
      correct: [this.answer.correct],
    });
  }

  onSubmit(action: string, answerID: number) {
    if (action === ActionType.UPDATE) this.saveAnswer(answerID);
    if (action === ActionType.DELETE) this.deleteAnswer(answerID);
  }

  saveAnswer(answerID: number) {
    const answerFormValue = this.answerForm.value;
    this.answerStore.saveAnswer(answerID, answerFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Answer updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating answer.'),
    });
  }

  deleteAnswer(answerID: number) {
    this.answerStore.deleteAnswer(answerID).subscribe({
      next: () => {
        this.justificationStore.removeAnswerID();
        this.snackbarService.openSnackBar('Concept deleted.');
      },
      error: () => this.snackbarService.openSnackBar('Error deleting concept.'),
    });
  }

  openDeleteDialog(answerID: number) {
    const dialogRef = this.dialog.open(KnowledgeDeleteDialogComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'answer',
      },
    });

    dialogRef.afterClosed().subscribe(deleteConcept => {
      if (deleteConcept) this.deleteAnswer(answerID);
    });
  }
}
