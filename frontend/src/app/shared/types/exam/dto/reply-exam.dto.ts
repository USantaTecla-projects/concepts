import { ExamQuestionAndAnswerDTO } from './exam-question-answer.dto';

export interface ReplyExamDTO {
  examID: number;
  userID: number;
  creationDate: string;
  questionAndAnswerDTOList: ExamQuestionAndAnswerDTO[];
  corrected: boolean;
}
