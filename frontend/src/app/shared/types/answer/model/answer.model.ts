import { QuestionAnswerType } from '../../question/enum/question-answer-type.enum';
import { AnswerCorrectionStatus } from '../enum/answer-correction-status.enum';

export interface Answer {
  id: number;
  type: QuestionAnswerType;
  userID: number;
  correctionStatus: AnswerCorrectionStatus;
}
