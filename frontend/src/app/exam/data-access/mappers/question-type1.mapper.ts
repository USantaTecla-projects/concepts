import { QuestionData } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType1 } from '../../types/model/question/question-type/question-type1.model';
import { ExamQuestionType1Component } from '../../ui/exam-question/exam-question-types/exam-question-type1/exam-question-type1.component';

export const mapQuestionType1 = (questionData: QuestionData): QuestionType1 => {
  const { id, conceptID, definitionID, conceptText, incorrectDefinitionText } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE1,
    conceptID,
    definitionID,
    componentType: ExamQuestionType1Component,
    conceptText,
    incorrectDefinitionText,
  };
};
