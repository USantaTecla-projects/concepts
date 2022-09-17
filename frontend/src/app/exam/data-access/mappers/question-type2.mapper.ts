import { QuestionData } from '../../types/dto/question.dto';
import { QuestionType2 } from '../../types/model/question/question-type/question-type2.dto ';
import { ExamQuestionType2Component } from '../../ui/question/exam-question-type2/exam-question-type2.component';

export function mapQuestionType2(questionData: QuestionData): QuestionType2 {
  const { conceptText, answerText } = questionData;
  return { type: ExamQuestionType2Component, conceptText, answerText };
}
