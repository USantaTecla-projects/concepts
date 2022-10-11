import { QuestionData } from './question.dto';

export interface ExamData {
  id: number;
  userID: number;
  creationDate: string;
  questionDataList: QuestionData[];
}
