import { Component, Input } from '@angular/core';
import { CorrectionQuestionReplierService } from 'src/app/correction/data-access/correction-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { AnswerT2 } from 'src/app/shared/types/answer/model/answer-type2.model';
import { QuestionType2 } from 'src/app/shared/types/question/model/question-type2.model';
import { CorrectionQuestionComponent } from '../correction-question.component';

@Component({
  selector: 'app-correction-question-type2',
  templateUrl: './correction-question-type2.component.html',
  styleUrls: ['./correction-question-type2.component.scss', '../correction-question.component.scss'],
})
export class CorrectionQuestionType2Component implements CorrectionQuestionComponent {
  @Input() question!: QuestionType2;

  @Input() answer!: AnswerT2;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  constructor(private correctionQuestionReplierService: CorrectionQuestionReplierService) {}

  correctQuestion(isCorrect: boolean) {
    this.answer.correctionStatus = isCorrect ? AnswerCorrectionStatus.Correct : AnswerCorrectionStatus.Incorrect;

    this.correctionQuestionReplierService.addCorrectedQuestion({ question: this.question, answer: this.answer });
  }
}
