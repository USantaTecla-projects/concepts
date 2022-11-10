import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamQuestionReplierService } from 'src/app/exam/data-access/exam-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { QuestionAnswerType } from 'src/app/shared/types/question/enum/question-answer-type.enum';
import { QuestionType3 } from 'src/app/shared/types/question/model/question-type3.model';

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

  constructor(private formBuilder: FormBuilder, private examQuestionReplierService: ExamQuestionReplierService) {}

  ngOnInit(): void {
    this.questionReplyForm = this.formBuilder.group({
      bool: [null],
    });

    this.examQuestionReplierService.replyQuestions$.subscribe(reply => {
      if (reply) this.onQuestionReplied();
    });
  }

  onQuestionReplied() {
    const questionReplyFormValue = this.questionReplyForm.value;
    this.question = {
      ...this.question,
      answer: {
        ...this.question.answer,
        type: QuestionAnswerType.TYPE3,
        correctionStatus: AnswerCorrectionStatus.Pending,
        reply: questionReplyFormValue.bool ? questionReplyFormValue.bool === '0' : null,
        userID: this.userID,
      },
    };
    this.examQuestionReplierService.addRepliedQuestion(this.question);
  }
}
