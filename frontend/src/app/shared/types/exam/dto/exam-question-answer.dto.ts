import { AnswerDTO } from '../../answer/dto/answer.dto';
import { QuestionDTO } from '../../question/dto/question.dto';

export interface ExamQuestionAndAnswerDTO {
  questionDTO: QuestionDTO;
  answerDTO: AnswerDTO;
}
