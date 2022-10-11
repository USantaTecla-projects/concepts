import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionType1 } from 'src/app/exam/types/model/question/question-type/question-type1.dto';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type1',
  templateUrl: './exam-question-type1.component.html',
  styleUrls: ['./exam-question-type1.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType1Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType1;

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
