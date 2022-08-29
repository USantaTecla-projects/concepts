import { Component, OnInit } from '@angular/core';
import { AnswerStore } from '../data-access/answer.store';
import { ConceptStore } from '../data-access/concept.store';
import { JustificationStore } from '../data-access/justification.store';

@Component({
  selector: 'app-knowledge-manager',
  templateUrl: './knowledge-manager.page.html',
  styleUrls: ['./knowledge-manager.page.scss'],
})
export class KnowledgeManagerPage implements OnInit {
  constructor(
    public conceptStore: ConceptStore,
    public answerStore: AnswerStore,
    public justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {}
}
