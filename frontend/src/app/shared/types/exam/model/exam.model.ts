import { Answer } from '../../answer/model/answer.model';
import { Question } from '../../question/model/question.model';

export interface Exam {
  id: number;
  userID: number;
  creationDate: string;
  questionList: Question[];
  answerList: Answer[];
  timeSpent?: number;
  corrected: boolean;
  mark: string;
}
