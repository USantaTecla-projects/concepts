import { Component, Input, OnInit } from '@angular/core';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { AnswerStore } from '../../data-access/answer.store';
import { Concept, ConceptStore } from '../../data-access/concept.store';
import { JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-concept-list',
  templateUrl: './knowledge-concept-list.component.html',
  styleUrls: ['./knowledge-concept-list.component.scss'],
})
export class KnowledgeConceptListComponent implements OnInit {
  @Input() concepts: Concept[] | null = [];

  state: string = State.INIT;

  constructor(
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
}
