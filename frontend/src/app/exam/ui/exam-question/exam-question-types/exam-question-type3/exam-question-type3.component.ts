import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { QuestionReplierService } from 'src/app/exam/data-access/question-replier.service';
import { QuestionAnswerType } from 'src/app/exam/types/enum/question-answer-type.enum';
import { QuestionT3ReplyForm } from 'src/app/exam/types/model/form/questionT3ReplyForm.model';
import { QuestionType3 } from 'src/app/exam/types/model/question/question-type/question-type3.model';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type3',
  templateUrl: './exam-question-type3.component.html',
  styleUrls: ['./exam-question-type3.component.scss', '../exam-question.component.scss'],
})
export class ExamQuestionType3Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType3;

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
      bool: [null],
    });

    this.questionReplierService.replyQuestions$.subscribe(reply => {
      if (reply) this.onQuestionReplied();
    });
  }

  onQuestionReplied() {
    const questionReplyFormValue: QuestionT3ReplyForm = this.questionReplyForm.value;
    this.question = {
      ...this.question,
      answer: {
        type: QuestionAnswerType.TYPE3,
        reply: questionReplyFormValue.bool === '0',
        userID: this.userID,
      },
    };
    this.questionReplierService.addRepliedQuestion(this.question);
  }
}
