import { QuestionData } from '../../types/dto/question.dto';
import { QuestionType2 } from '../../types/model/question/question-type/question-type2.dto ';
import { ExamQuestionType2Component } from '../../ui/exam-question/exam-question-types/exam-question-type2/exam-question-type2.component';

export function mapQuestionType2(questionData: QuestionData): QuestionType2 {
  const { id, conceptID, definitionID, conceptText, definitionText } = questionData;
  return { id, conceptID, definitionID, type: ExamQuestionType2Component, conceptText, definitionText };
}
