import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamQuestionReplierService } from 'src/app/exam/data-access/exam-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/exam/types/enum/answer-correction-status.enum';
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

  constructor(private formBuilder: FormBuilder, private examQuestionReplierService: ExamQuestionReplierService) {}

  ngOnInit(): void {
    this.questionReplyForm = this.formBuilder.group({
      text: [null],
    });

    this.examQuestionReplierService.replyQuestions$.subscribe(reply => {
      if (reply) this.onQuestionReplied();
    });
  }

  onQuestionReplied() {
    const questionReplyFormValue: QuestionT0ReplyForm = this.questionReplyForm.getRawValue();
    this.question = {
      ...this.question,
      answer: {
        type: QuestionAnswerType.TYPE0,
        correctionStatus: AnswerCorrectionStatus.Pending,
        reply: questionReplyFormValue.text ? questionReplyFormValue.text : null,
        userID: this.userID,
      },
    };
    this.examQuestionReplierService.addRepliedQuestion(this.question);
  }
}
