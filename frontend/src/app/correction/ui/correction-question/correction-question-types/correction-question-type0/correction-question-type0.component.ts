import { Component, Input } from '@angular/core';
import { CorrectionQuestionReplierService } from 'src/app/correction/util/correction-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { AnswerT0 } from 'src/app/shared/types/answer/model/answer-type0.model';
import { QuestionType0 } from 'src/app/shared/types/question/model/question-type0.model';
import { CorrectionQuestionComponent } from '../correction-question.component';

@Component({
  selector: 'app-correction-question-type0',
  templateUrl: './correction-question-type0.component.html',
  styleUrls: ['./correction-question-type0.component.scss', '../correction-question.component.scss'],
})
export class CorrectionQuestionType0Component implements CorrectionQuestionComponent {
  @Input() question!: QuestionType0;

  @Input() answer!: AnswerT0;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  constructor(private correctionQuestionReplierService: CorrectionQuestionReplierService) {}

  correctQuestion(isCorrect: boolean) {
    this.answer.correctionStatus = isCorrect ? AnswerCorrectionStatus.Correct : AnswerCorrectionStatus.Incorrect;

    this.correctionQuestionReplierService.addCorrectedQuestion({ question: this.question, answer: this.answer });
  }
}
