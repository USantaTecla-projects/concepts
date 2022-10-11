import { QuestionData } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType2 } from '../../types/model/question/question-type/question-type2.model';
import { ExamQuestionType2Component } from '../../ui/exam-question/exam-question-types/exam-question-type2/exam-question-type2.component';

export const mapQuestionType2 = (questionData: QuestionData): QuestionType2 => {
  const { id, conceptID, definitionID, conceptText, definitionText } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE2,
    conceptID,
    definitionID,
    componentType: ExamQuestionType2Component,
    conceptText,
    definitionText,
  };
};
