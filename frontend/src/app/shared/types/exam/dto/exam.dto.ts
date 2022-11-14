import { AnswerDTO } from '../../answer/dto/answer.dto';
import { QuestionDTO } from '../../question/dto/question.dto';

export interface ExamData {
  id: number;
  userID: number;
  creationDate: string;
  questionList: QuestionDTO[];
  answerList: AnswerDTO[];
  timeSpent?: number;
  corrected: boolean;
  mark: string;
}
