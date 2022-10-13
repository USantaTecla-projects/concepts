import { QuestionAnswerType } from '../enum/question-answer-type.enum';

export interface AnswerDTO {
  userID: number;
  type: QuestionAnswerType;
  reply: any;
}
