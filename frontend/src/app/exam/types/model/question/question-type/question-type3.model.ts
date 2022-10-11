import { AnswerT3 } from '../../answer/answer-type/answer-type3.model';
import { Question } from '../question.model';

export interface QuestionType3 extends Question {
  conceptID: number;
  definitionID: number;
  justificationID: number;
  conceptText: string;
  incorrectDefinitionText: string;
  justificationText: string;
  answer?: AnswerT3;
}
