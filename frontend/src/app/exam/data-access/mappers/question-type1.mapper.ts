import { QuestionData } from '../../types/dto/question.dto';
import { QuestionType1 } from '../../types/model/question/question-type/question-type1.dto';
import { ExamQuestionType1Component } from '../../ui/exam-question/exam-question-types/exam-question-type1/exam-question-type1.component';

export function mapQuestionType1(questionData: QuestionData): QuestionType1 {
  const { id, conceptID, definitionID, conceptText, incorrectDefinitionText } = questionData;
  return { id, conceptID, definitionID, type: ExamQuestionType1Component, conceptText, incorrectDefinitionText };
}
