import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { State } from 'src/app/shared/utils/state.enum';
import { Answer, AnswerStore } from '../../data-access/answer.store';
import { JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-answer-list',
  templateUrl: './knowledge-answer-list.component.html',
  styleUrls: ['./knowledge-answer-list.component.scss'],
})
export class KnowledgeAnswerListComponent implements OnInit {
  @Input() answers: Answer[] | null = [];

  @Output() conceptSelect: EventEmitter<Observable<number>> = new EventEmitter<Observable<number>>();

  state: string = State.INIT;

  selectedItemId$!: Observable<number>;

  editAnswerForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private answerStore: AnswerStore,
    private justificationStore: JustificationStore
  ) {}

  ngOnInit(): void {
    this.editAnswerForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [null],
    });

    this.answerStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  selectItem(answerID: number) {
    this.justificationStore.setAnswerId(answerID);
  }

  onAnswerEdit(conceptDTO: any) {}
}
