import { QuestionAnswerType } from '../../enum/question-answer-type.enum';

export interface Answer {
  type: QuestionAnswerType;
  userID: number;
}
