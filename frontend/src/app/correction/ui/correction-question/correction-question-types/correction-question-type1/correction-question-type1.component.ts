import { Component, Input } from '@angular/core';
import { CorrectionQuestionReplierService } from 'src/app/correction/data-access/correction-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { QuestionType1 } from 'src/app/shared/types/question/model/question-type1.model';
import { CorrectionQuestionComponent } from '../correction-question.component';

@Component({
  selector: 'app-correction-question-type1',
  templateUrl: './correction-question-type1.component.html',
  styleUrls: ['./correction-question-type1.component.scss', '../correction-question.component.scss'],
})
export class CorrectionQuestionType1Component implements CorrectionQuestionComponent {
  @Input() question!: QuestionType1;

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
