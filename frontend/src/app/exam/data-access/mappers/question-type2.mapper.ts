import { QuestionDTO } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enum/question-answer-type.enum';
import { QuestionType2 } from '../../types/model/question/question-type/question-type2.model';
import { Question } from '../../types/model/question/question.model';
import { ExamQuestionType2Component } from '../../ui/exam-question/exam-question-types/exam-question-type2/exam-question-type2.component';

export const mapToQuestionType2 = (questionData: QuestionDTO): QuestionType2 => {
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

export const mapFromQuestionType2 = (question: Question): QuestionDTO => {
  const questionType2 = <QuestionType2>question;
  const { type, conceptID, definitionID } = questionType2;
  return {
    type,
    conceptID,
    definitionID,
  };
};
