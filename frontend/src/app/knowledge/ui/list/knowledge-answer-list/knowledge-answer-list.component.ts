import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { Answer, AnswerStore } from '../../../data-access/answer.store';
import { JustificationStore } from '../../../data-access/justification.store';
import { KnowledgeDialogAnswerCreateComponent } from '../../dialog/knowledge-dialog-answer-create/knowledge-dialog-answer-create.component';

@Component({
  selector: 'app-knowledge-answer-list',
  templateUrl: './knowledge-answer-list.component.html',
  styleUrls: ['./knowledge-answer-list.component.scss'],
})
export class KnowledgeAnswerListComponent implements OnInit {
  @Input() answers: Answer[] | null = [];

  state: string = State.INIT;

  constructor(
    private answerStore: AnswerStore,
    private justificationStore: JustificationStore,
    private snackbarService: SnackbarService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.answerStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  selectAnswer(answerID: number) {
    this.justificationStore.setAnswerID(this.answerStore.conceptID, answerID);
  }

  createAnswer(answerFormValue: any) {
    this.answerStore.createAnswer(answerFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Answer created.'),
      error: () => this.snackbarService.openSnackBar('Error creating answer.'),
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(KnowledgeDialogAnswerCreateComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'concept',
      },
    });

    dialogRef.afterClosed().subscribe(answerFormValue => {
      if (answerFormValue) this.createAnswer(answerFormValue);
    });
  }
}
