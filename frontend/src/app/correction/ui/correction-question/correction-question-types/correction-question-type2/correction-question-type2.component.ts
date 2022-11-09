import { Component, Input } from '@angular/core';
import { CorrectionQuestionReplierService } from 'src/app/correction/data-access/correction-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/exam/types/enum/answer-correction-status.enum';
import { QuestionType2 } from 'src/app/exam/types/model/question/question-type/question-type2.model';
import { CorrectionQuestionComponent } from '../correction-question.component';

@Component({
  selector: 'app-correction-question-type2',
  templateUrl: './correction-question-type2.component.html',
  styleUrls: ['./correction-question-type2.component.scss', '../correction-question.component.scss'],
})
export class CorrectionQuestionType2Component implements CorrectionQuestionComponent {
  @Input() question!: QuestionType2;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  constructor(private correctionQuestionReplierService: CorrectionQuestionReplierService) {}

  correctQuestion(isCorrect: boolean) {
    this.question.answer.correctionStatus = isCorrect
      ? AnswerCorrectionStatus.Correct
      : AnswerCorrectionStatus.Incorrect;

    this.correctionQuestionReplierService.addCorrectedQuestion(this.question);
  }
}
