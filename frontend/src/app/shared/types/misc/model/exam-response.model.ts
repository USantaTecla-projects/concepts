import { Answer } from '../../answer/model/answer.model';
import { Question } from '../../question/model/question.model';

export interface ExamResponse {
  question: Question;
  answer: Answer;
}
