import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionReplierService } from 'src/app/exam/data-access/question-replier.service';
import { QuestionAnswerType } from 'src/app/exam/types/enum/question-answer-type.enum';
import { QuestionT0ReplyForm } from 'src/app/exam/types/model/form/questionT0ReplyForm.model';
import { QuestionType0 } from 'src/app/exam/types/model/question/question-type/question-type0.model';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type0',
  templateUrl: './exam-question-type0.component.html',
  styleUrls: ['./exam-question-type0.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType0Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType0;

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
    const questionReplyFormValue: QuestionT0ReplyForm = this.questionReplyForm.getRawValue();
    this.question = {
      ...this.question,
      answer: { type: QuestionAnswerType.TYPE0, reply: questionReplyFormValue.text, userID: this.userID },
    };
    this.questionReplierService.addRepliedQuestion(this.question);
  }
}
