import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Answer } from 'src/app/knowledge/data-access/model/answer.model';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { AnswerStore } from '../../../data-access/answer.store';
import { KnowledgeDialogAnswerCreateComponent } from '../../dialog/knowledge-dialog-answer-create/knowledge-dialog-answer-create.component';
import { KnowledgeDialogDeleteComponent } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.component';

@Component({
  selector: 'app-knowledge-answer-list',
  templateUrl: './knowledge-answer-list.component.html',
  styleUrls: ['./knowledge-answer-list.component.scss'],
})
export class KnowledgeAnswerListComponent implements OnInit, OnChanges {
  @Input() answersList!: Answer[] | null;

  @Input() resetList: boolean = false;

  @Output() selectAnswer: EventEmitter<number> = new EventEmitter();

  @Output() createAnswer: EventEmitter<Answer> = new EventEmitter();

  @Output() updateAnswer: EventEmitter<Answer> = new EventEmitter();

  @Output() deleteAnswer: EventEmitter<number> = new EventEmitter();

  state: string = State.INIT;

  selectedAnswerID!: number;

  constructor(private answerStore: AnswerStore, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.answerStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const resetListChange = changes['resetList'];
    if (resetListChange) {
      this.selectedAnswerID = 0;
    }
  }

  onAnswerSelect(answerID: number): void {
    this.selectedAnswerID = answerID;
    this.selectAnswer.emit(this.selectedAnswerID);
  }

  onAnswerCreate(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogAnswerCreateComponent, {
      width: '30rem',
      data: {
        knowledgeItem: 'answer',
      },
    });

    dialogRef.afterClosed().subscribe(answerFormValue => {
      if (answerFormValue) {
        const newAnswer: Answer = { ...answerFormValue };
        this.createAnswer.emit(newAnswer);
      }
    });
  }

  onAnswerUpdate(updatedAnswer: Answer) {
    this.updateAnswer.emit(updatedAnswer);
  }

  onAnswerDelete(): void {
    const dialogRef = this.dialog.open(KnowledgeDialogDeleteComponent, {
      width: '20rem',
      data: {
        knowledgeItem: 'answer',
      },
    });

    dialogRef.afterClosed().subscribe(deleteAnswer => {
      if (deleteAnswer) this.deleteAnswer.emit(this.selectedAnswerID);
    });
  }
}
