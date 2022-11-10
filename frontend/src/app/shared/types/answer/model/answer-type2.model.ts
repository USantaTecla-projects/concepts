import { Answer } from './answer.model';

export interface AnswerT2 extends Answer {
  reply: boolean | null;
}
