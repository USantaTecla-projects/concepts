import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Answer } from 'src/app/knowledge/types/answer.model';

@Component({
  selector: 'app-knowledge-answer-update-form',
  templateUrl: './knowledge-answer-update-form.component.html',
  styleUrls: ['./knowledge-answer-update-form.component.scss'],
})
export class KnowledgeAnswerUpdateFormComponent implements OnInit {
  @Input() answer!: Answer;

  @Output() updateAnswer: EventEmitter<Answer> = new EventEmitter();

  answerForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.answerForm = this.formBuilder.group({
      text: [this.answer.text, [Validators.required]],
      correct: [this.answer.correct],
    });
  }

  onSubmit(): void {
    const answerFormValue = this.answerForm.value;
    if (answerFormValue) {
      const updatedAnswer: Answer = { ...answerFormValue };
      this.updateAnswer.emit(updatedAnswer);
    }
  }
}
