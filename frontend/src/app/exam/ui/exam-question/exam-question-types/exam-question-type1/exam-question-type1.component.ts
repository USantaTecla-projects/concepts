import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamQuestionReplierService } from 'src/app/exam/utils/exam-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { QuestionAnswerType } from 'src/app/shared/types/question/enum/question-answer-type.enum';
import { QuestionType1 } from 'src/app/shared/types/question/model/question-type1.model';

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
    const questionReplyFormValue = this.questionReplyForm.value;

    const response = {
      question: { ...this.question },
      answer: {
        type: QuestionAnswerType.TYPE1,
        correctionStatus: AnswerCorrectionStatus.Pending,
        reply: questionReplyFormValue.text ? questionReplyFormValue.text : null,
        userID: this.userID,
        questionID: this.question.id,
      },
    };

    this.examQuestionReplierService.addRepliedQuestion(response);
  }
}
