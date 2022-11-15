import { Component, Input } from '@angular/core';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { Answer } from 'src/app/shared/types/answer/model/answer.model';
import { Question } from 'src/app/shared/types/question/model/question.model';

@Component({
  selector: 'app-exam-history-item',
  templateUrl: './exam-history-item.component.html',
  styleUrls: ['./exam-history-item.component.scss'],
})
export class ExamHistoryItemComponent {
  @Input() title!: string;

  @Input() mark!: string;

  @Input() date!: string;

  @Input() timeSpent!: number | undefined;

  @Input() isOffline: boolean = false;

  @Input() questionList!: Question[];

  @Input()
  set answerList(answers: Answer[]) {
    this.correctQuestions = answers.filter(answer => answer.correctionStatus === AnswerCorrectionStatus.Correct).length;
  }

  correctQuestions!: number;

  constructor() {}
}
