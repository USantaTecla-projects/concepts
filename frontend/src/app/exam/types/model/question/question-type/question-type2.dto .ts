import { Question } from '../question.model';

export interface QuestionType2 extends Question {
  conceptText: string;
  definitionText: string;
}
