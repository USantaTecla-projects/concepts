import { QuestionAnswerType } from '../enums/question-answer-type.enum';

export interface QuestionData {
  type: QuestionAnswerType;
  [key: string]: any;
}
