import { QuestionAnswerType } from '../enum/question-answer-type.enum';

export interface QuestionDTO {
  type: QuestionAnswerType;
  [key: string]: any;
}
