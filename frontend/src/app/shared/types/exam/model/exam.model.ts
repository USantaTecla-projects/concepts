import { Question } from '../../question/model/question.model';

export interface Exam {
  id: number;
  userID: number;
  creationDate: string;
  questionList: Question[];
  timeSpent?: number;
  corrected: boolean;
  mark: string;
}
