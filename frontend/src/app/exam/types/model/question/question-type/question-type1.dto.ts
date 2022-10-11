import { Question } from '../question.model';

export interface QuestionType1 extends Question {
  conceptID: number;
  definitionID: number;
  conceptText: string;
  incorrectDefinitionText: string;
}
