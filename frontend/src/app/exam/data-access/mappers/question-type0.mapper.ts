import { QuestionData } from '../../types/dto/question.dto';
import { QuestionType0 } from '../../types/model/question/question-type/question-type0.dto';
import { ExamQuestionType0Component } from '../../ui/exam-question/exam-question-types/exam-question-type0/exam-question-type0.component';

export function mapQuestionType0(questionData: QuestionData): QuestionType0 {
  const { id, conceptID, conceptText } = questionData;
  return { id, conceptID, type: ExamQuestionType0Component, conceptText };
}
