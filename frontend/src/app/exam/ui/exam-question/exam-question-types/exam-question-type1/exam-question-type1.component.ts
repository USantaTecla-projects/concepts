import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionReplierService } from 'src/app/exam/data-access/question-replier.service';
import { QuestionAnswerType } from 'src/app/exam/types/enum/question-answer-type.enum';
import { QuestionT1ReplyForm } from 'src/app/exam/types/model/form/questionT1ReplyForm.model';
import { QuestionType1 } from 'src/app/exam/types/model/question/question-type/question-type1.model';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type1',
  templateUrl: './exam-question-type1.component.html',
  styleUrls: ['./exam-question-type1.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType1Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType1;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  questionReplyForm: FormGroup = new FormGroup({});

  constructor(
    public examStore: ExamStore,
    private formBuilder: FormBuilder,
    private questionReplierService: QuestionReplierService
  ) {}

  ngOnInit(): void {
    this.questionReplyForm = this.formBuilder.group({
      text: [null],
    });

    this.questionReplierService.replyQuestions$.subscribe(reply => {
      if (reply) this.onQuestionReplied();
    });
  }

  onQuestionReplied() {
    const questionReplyFormValue: QuestionT1ReplyForm = this.questionReplyForm.value;
    this.question = {
      ...this.question,
      answer: { type: QuestionAnswerType.TYPE1, reply: questionReplyFormValue.text, userID: this.userID },
    };

    this.questionReplierService.addRepliedQuestion(this.question);
  }
}
