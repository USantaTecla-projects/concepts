import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionType2 } from 'src/app/exam/types/model/question/question-type/question-type2.dto ';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type2',
  templateUrl: './exam-question-type2.component.html',
  styleUrls: ['./exam-question-type2.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType2Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType2;

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
