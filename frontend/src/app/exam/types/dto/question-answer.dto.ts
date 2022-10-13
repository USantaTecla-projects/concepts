import { AnswerDTO } from './answer.dto';
import { QuestionDTO } from './question.dto';

export interface QuestionAndAnswerDTO {
  questionDTO: QuestionDTO;
  answerDTO: AnswerDTO;
}
