import { QuestionData } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType3 } from '../../types/model/question/question-type/question-type3.model';
import { ExamQuestionType3Component } from '../../ui/exam-question/exam-question-types/exam-question-type3/exam-question-type3.component';

export const mapQuestionType3 = (questionData: QuestionData): QuestionType3 => {
  const { id, conceptID, definitionID, justificationID, conceptText, incorrectDefinitionText, justificationText } =
    questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE3,
    conceptID,
    definitionID,
    justificationID,
    componentType: ExamQuestionType3Component,
    conceptText,
    incorrectDefinitionText,
    justificationText,
  };
};
