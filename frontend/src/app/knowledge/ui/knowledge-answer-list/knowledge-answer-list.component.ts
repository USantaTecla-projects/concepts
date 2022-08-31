import { Component, Input, OnInit } from '@angular/core';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { Answer, AnswerStore } from '../../data-access/answer.store';
import { JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-answer-list',
  templateUrl: './knowledge-answer-list.component.html',
  styleUrls: ['./knowledge-answer-list.component.scss'],
})
export class KnowledgeAnswerListComponent implements OnInit {
  @Input() answers: Answer[] | null = [];

  state: string = State.INIT;

  constructor(private answerStore: AnswerStore, private justificationStore: JustificationStore) {}

  ngOnInit(): void {
    this.answerStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  selectAnswer(answerID: number) {
    this.justificationStore.setAnswerID(this.answerStore.conceptID, answerID);
  }
}
