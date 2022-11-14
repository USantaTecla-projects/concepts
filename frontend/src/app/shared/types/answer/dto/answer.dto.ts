import { QuestionAnswerType } from '../../question/enum/question-answer-type.enum';
import { AnswerCorrectionStatus } from '../enum/answer-correction-status.enum';

export interface AnswerDTO {
  id: number;
  userID: number;
  type: QuestionAnswerType;
  reply: any;
  correctionStatus: AnswerCorrectionStatus;
  [key: string]: any;
}
