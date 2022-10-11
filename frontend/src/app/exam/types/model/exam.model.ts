import { QuestionTypeX } from '../question-typeX.type';

export interface Exam {
  id?: number;
  userID?: number;
  creationDate?: string;
  questionList: QuestionTypeX[];
}
