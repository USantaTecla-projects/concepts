import { QuestionType } from '../enums/question-type.enum';

export interface QuestionData {
  type: QuestionType;
  [key: string]: any;
}
