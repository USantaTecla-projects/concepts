import { AnswerT0 } from '../../answer/model/answer-type0.model';
import { Question } from './question.model';

export interface QuestionType0 extends Question {
  conceptID: number;
  conceptText: string;
  answer: AnswerT0;
}
