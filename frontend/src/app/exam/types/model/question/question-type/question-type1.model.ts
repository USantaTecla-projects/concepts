import { AnswerT1 } from '../../answer/answer-type/answer-type1.model';
import { Question } from '../question.model';

export interface QuestionType1 extends Question {
  conceptID: number;
  definitionID: number;
  conceptText: string;
  incorrectDefinitionText: string;
  answer: AnswerT1;
}
