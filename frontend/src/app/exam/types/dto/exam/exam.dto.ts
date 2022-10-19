import { QuestionDTO } from '../question.dto';

export interface ExamData {
  id: number;
  userID: number;
  creationDate: string;
  questionDataList: QuestionDTO[];
  timeSpent?: number;
}
