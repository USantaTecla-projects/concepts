import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
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

  @Output() conceptSelect: EventEmitter<Observable<number>> = new EventEmitter<Observable<number>>();

  editConceptForm: FormGroup = new FormGroup({});

  state: string = State.INIT;

  constructor(
    private formBuilder: FormBuilder,
    private answerStore: AnswerStore,
    private justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {
    this.editConceptForm = this.formBuilder.group({
      text: [null, [Validators.required]],
    });
  }

  selectItem(conceptID: number) {
    this.answerStore.setConceptId(conceptID);
    this.justificationStore.removeAnswerId();
  }

  onConceptEdit(conceptDTO: any) {}
}
