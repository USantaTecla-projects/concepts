import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Answer } from 'src/app/knowledge/interfaces/answer.model';

@Component({
  selector: 'app-knowledge-answer-create-form',
  templateUrl: './knowledge-answer-create-form.component.html',
  styleUrls: ['./knowledge-answer-create-form.component.scss'],
})
export class KnowledgeAnswerCreateFormComponent implements OnInit {
  @Output() createAnswer: EventEmitter<Answer> = new EventEmitter();

  answerForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.answerForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [false],
    });
  }

  onSubmit(): void {
    const answerFormValue = this.answerForm.value;
    if (answerFormValue) {
      const newAnswer: Answer = { ...answerFormValue };
      this.createAnswer.emit(newAnswer);
    }
  }

  onCancel(): void {
    this.createAnswer.emit();
  }
}
