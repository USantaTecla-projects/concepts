import { AnswerT2 } from '../../answer/answer-type/answer-type2.model';
import { Question } from '../question.model';

export interface QuestionType2 extends Question {
  conceptID: number;
  definitionID: number;
  conceptText: string;
  definitionText: string;
  answer: AnswerT2;
}
