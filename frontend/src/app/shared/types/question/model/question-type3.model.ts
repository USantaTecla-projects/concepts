import { Question } from './question.model';

export interface QuestionType3 extends Question {
  conceptID: number;
  definitionID: number;
  justificationID: number;
  conceptText: string;
  incorrectDefinitionText: string;
  justificationText: string;
}
