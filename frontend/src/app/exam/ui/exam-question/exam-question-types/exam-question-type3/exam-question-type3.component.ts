import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionType3 } from 'src/app/exam/types/model/question/question-type/question-type3.dto';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type3',
  templateUrl: './exam-question-type3.component.html',
  styleUrls: ['./exam-question-type3.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType3Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType3;

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
