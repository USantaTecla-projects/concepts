import { QuestionAnswerType } from '../enum/question-answer-type.enum';

export interface QuestionDTO {
  id: number;
  type: QuestionAnswerType;
  [key: string]: any;
}
