import { AnswerDTO } from '../../answer/dto/answer.dto';
import { QuestionDTO } from '../../question/dto/question.dto';

export interface ReplyExamDTO {
  examID: number;
  userID: number;
  creationDate: string;
  questionDTOList: QuestionDTO[];
  answerDTOList: AnswerDTO[];
  corrected: boolean;
}
