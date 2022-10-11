import { QuestionAnswerType } from '../../enums/question-answer-type.enum';

export interface Answer {
  type: QuestionAnswerType;
  userID: number;
}
