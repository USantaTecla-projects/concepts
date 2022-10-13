import { QuestionDTO } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enum/question-answer-type.enum';
import { QuestionType1 } from '../../types/model/question/question-type/question-type1.model';
import { Question } from '../../types/model/question/question.model';
import { ExamQuestionType1Component } from '../../ui/exam-question/exam-question-types/exam-question-type1/exam-question-type1.component';

export const mapToQuestionType1 = (questionData: QuestionDTO): QuestionType1 => {
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

export const mapFromQuestionType1 = (question: Question): QuestionDTO => {
  const questionType1 = <QuestionType1>question;
  const { type, conceptID, definitionID } = questionType1;
  return {
    type,
    conceptID,
    definitionID,
  };
};
