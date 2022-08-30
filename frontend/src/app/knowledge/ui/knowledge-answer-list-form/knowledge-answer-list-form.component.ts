import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { Answer, AnswerStore } from '../../data-access/answer.store';
import { JustificationStore } from '../../data-access/justification.store';

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
    private answerStore: AnswerStore
  ) {}

  ngOnInit(): void {
    this.answerForm = this.formBuilder.group({
      text: [this.answer.text, [Validators.required]],
      correct: [this.answer.correct],
    });
  }

  saveAnswer(answerID: number) {
    const answerFormValue = this.answerForm.value;
    this.answerStore.saveAnswer(answerID, answerFormValue).subscribe({
      next: () => this.snackbarService.openSnackBar('Concept updated.'),
      error: () => this.snackbarService.openSnackBar('Error updating concept.'),
    });
  }
}
