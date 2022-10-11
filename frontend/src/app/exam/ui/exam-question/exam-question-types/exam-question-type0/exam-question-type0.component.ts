import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionType0 } from 'src/app/exam/types/model/question/question-type/question-type0.dto';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type0',
  templateUrl: './exam-question-type0.component.html',
  styleUrls: ['./exam-question-type0.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType0Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType0;

  @Input() questionNumber!: number;

  @Input() set replyQuestion(val: boolean) {
    this.onQuestionReplied();
  }

  questionReplyForm: FormGroup = new FormGroup({});

  constructor(public examStore: ExamStore, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.questionReplyForm = this.formBuilder.group({
      text: [null],
    });
  }

  onQuestionReplied() {
    const questionReplyFormValue = this.questionReplyForm.value;
    this.examStore.replyQuestion(this.question, questionReplyFormValue);
  }
}
