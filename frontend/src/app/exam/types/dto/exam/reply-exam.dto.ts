import { QuestionAndAnswerDTO } from '../question-answer.dto';

export interface ReplyExamDTO {
  examID: number;
  userID: number;
  creationDate: string;
  questionAndAnswerDTOList: QuestionAndAnswerDTO[];
}
