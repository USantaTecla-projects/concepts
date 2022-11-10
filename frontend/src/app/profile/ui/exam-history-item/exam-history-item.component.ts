import { Component, Input, OnInit } from '@angular/core';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { Question } from 'src/app/shared/types/question/model/question.model';

@Component({
  selector: 'app-exam-history-item',
  templateUrl: './exam-history-item.component.html',
  styleUrls: ['./exam-history-item.component.scss'],
})
export class ExamHistoryItemComponent implements OnInit {
  @Input() title!: string;

  @Input() mark!: string;

  @Input() date!: string;

  @Input() timeSpent!: number | undefined;

  @Input() isOffline: boolean = false;

  @Input() questionList!: Question[];

  correctQuestions!: number;

  constructor() {}

  ngOnInit(): void {
    this.correctQuestions = this.questionList.filter(
      question => question['answer'].correctionStatus === AnswerCorrectionStatus.Correct
    ).length;
  }
}
