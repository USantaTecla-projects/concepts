import { Component, Input } from '@angular/core';
import { CorrectionQuestionReplierService } from 'src/app/correction/util/correction-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { AnswerT1 } from 'src/app/shared/types/answer/model/answer-type1.model';
import { QuestionType1 } from 'src/app/shared/types/question/model/question-type1.model';
import { CorrectionQuestionComponent } from '../correction-question.component';

@Component({
  selector: 'app-correction-question-type1',
  templateUrl: './correction-question-type1.component.html',
  styleUrls: ['./correction-question-type1.component.scss', '../correction-question.component.scss'],
})
export class CorrectionQuestionType1Component implements CorrectionQuestionComponent {
  @Input() question!: QuestionType1;

  @Input() answer!: AnswerT1;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  constructor(private correctionQuestionReplierService: CorrectionQuestionReplierService) {}

  correctQuestion(isCorrect: boolean) {
    this.answer.correctionStatus = isCorrect ? AnswerCorrectionStatus.Correct : AnswerCorrectionStatus.Incorrect;

    this.correctionQuestionReplierService.addCorrectedQuestion({ question: this.question, answer: this.answer });
  }
}
