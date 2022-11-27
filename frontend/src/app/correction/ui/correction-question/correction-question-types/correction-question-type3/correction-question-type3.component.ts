import { Component, Input } from '@angular/core';
import { CorrectionQuestionReplierService } from 'src/app/correction/util/correction-question-replier.service';
import { AnswerCorrectionStatus } from 'src/app/shared/types/answer/enum/answer-correction-status.enum';
import { AnswerT3 } from 'src/app/shared/types/answer/model/answer-type3.model';
import { QuestionType3 } from 'src/app/shared/types/question/model/question-type3.model';
import { CorrectionQuestionComponent } from '../correction-question.component';

@Component({
  selector: 'app-correction-question-type3',
  templateUrl: './correction-question-type3.component.html',
  styleUrls: ['./correction-question-type3.component.scss', '../correction-question.component.scss'],
})
export class CorrectionQuestionType3Component implements CorrectionQuestionComponent {
  @Input() question!: QuestionType3;

  @Input() answer!: AnswerT3;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  constructor(private correctionQuestionReplierService: CorrectionQuestionReplierService) {}

  correctQuestion(isCorrect: boolean) {
    this.answer.correctionStatus = isCorrect ? AnswerCorrectionStatus.Correct : AnswerCorrectionStatus.Incorrect;

    this.correctionQuestionReplierService.addCorrectedQuestion({ question: this.question, answer: this.answer });
  }
}
