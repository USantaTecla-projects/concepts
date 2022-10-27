import { QuestionDTO } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enum/question-answer-type.enum';
import { QuestionType0 } from '../../types/model/question/question-type/question-type0.model';
import { Question } from '../../types/model/question/question.model';
import { ExamQuestionType0Component } from '../../ui/exam-question/exam-question-types/exam-question-type0/exam-question-type0.component';

export const mapToQuestionType0 = (questionData: QuestionDTO): QuestionType0 => {
  const { id, conceptID, conceptText } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE0,
    conceptID,
    componentType: ExamQuestionType0Component,
    conceptText,
  };
};

export const mapFromQuestionType0 = (question: Question): QuestionDTO => {
  const questionType0 = <QuestionType0>question;
  const { type, conceptID } = questionType0;
  return {
    type,
    conceptID,
  };
};
