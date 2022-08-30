import { Component, Input, OnInit } from '@angular/core';
import { State } from 'src/app/shared/utils/state.enum';
import { AnswerStore } from '../../data-access/answer.store';
import { Concept } from '../../data-access/concept.store';
import { JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-concept-list',
  templateUrl: './knowledge-concept-list.component.html',
  styleUrls: ['./knowledge-concept-list.component.scss'],
})
export class KnowledgeConceptListComponent implements OnInit {
  @Input() concepts: Concept[] | null = [];

  state: string = State.INIT;

  constructor(private answerStore: AnswerStore, private justificationStore: JustificationStore) {}

  ngOnInit(): void {}

  selectConcept(conceptID: number) {
    this.answerStore.setConceptId(conceptID);
    this.justificationStore.removeAnswerId();
  }
}
