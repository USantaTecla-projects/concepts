import { Question } from '../question.model';

export interface QuestionType1 extends Question {
  conceptText: string;
  incorrectAnswerText: string;
}
