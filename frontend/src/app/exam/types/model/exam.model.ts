import { Question } from './question/question.model';

export interface Exam {
  id: number;
  userID: number;
  creationDate: string;
  questionList: Question[];
}
