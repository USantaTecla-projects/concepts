import { Question } from './question.model';

export interface QuestionType2 extends Question {
  conceptID: number;
  definitionID: number;
  conceptText: string;
  definitionText: string;
}
