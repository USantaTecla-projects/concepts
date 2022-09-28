import { Question } from '../question.model';

export interface QuestionType3 extends Question {
  conceptText: string;
  incorrectDefinitionText: string;
  justificationText: string;
}
