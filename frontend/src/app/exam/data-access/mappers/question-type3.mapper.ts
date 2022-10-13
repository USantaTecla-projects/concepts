import { QuestionDTO } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enum/question-answer-type.enum';
import { QuestionType3 } from '../../types/model/question/question-type/question-type3.model';
import { Question } from '../../types/model/question/question.model';
import { ExamQuestionType3Component } from '../../ui/exam-question/exam-question-types/exam-question-type3/exam-question-type3.component';

export const mapToQuestionType3 = (questionData: QuestionDTO): QuestionType3 => {
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

export const mapFromQuestionType3 = (question: Question): QuestionDTO => {
  const questionType3 = <QuestionType3>question;
  const { type, conceptID, definitionID, justificationID } = questionType3;
  return {
    type,
    conceptID,
    definitionID,
    justificationID,
  };
};
