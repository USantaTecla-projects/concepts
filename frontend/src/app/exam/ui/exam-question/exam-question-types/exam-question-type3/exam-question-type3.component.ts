import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ExamQuestionReplierService } from 'src/app/exam/data-access/exam-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/exam/types/enum/answer-correction-status.enum';
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
    const questionReplyFormValue: QuestionT3ReplyForm = this.questionReplyForm.value;
    this.question = {
      ...this.question,
      answer: {
        type: QuestionAnswerType.TYPE3,
        correctionStatus: AnswerCorrectionStatus.Pending,
        reply: questionReplyFormValue.bool ? questionReplyFormValue.bool === '0' : null,
        userID: this.userID,
      },
    };
    this.examQuestionReplierService.addRepliedQuestion(this.question);
  }
}
