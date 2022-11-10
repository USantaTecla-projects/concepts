import { QuestionDTO } from '../../question/dto/question.dto';

export interface ExamData {
  id: number;
  userID: number;
  creationDate: string;
  questionDataList: QuestionDTO[];
  timeSpent?: number;
  corrected: boolean;
  mark: string;
}
