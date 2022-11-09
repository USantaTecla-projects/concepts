import { CorrectionQuestionType0Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type0/correction-question-type0.component';
import { ExamQuestionType0Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type0/exam-question-type0.component';
import { QuestionDTO } from '../../../types/dto/question.dto';
import { QuestionAnswerType } from '../../../types/enum/question-answer-type.enum';
import { QuestionType0 } from '../../../types/model/question/question-type/question-type0.model';
import { Question } from '../../../types/model/question/question.model';

export const mapToQuestionType0 = (questionData: QuestionDTO): QuestionType0 => {
  const { id, conceptID, conceptText, answerT0 } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE0,
    conceptID,
    examComponentType: ExamQuestionType0Component,
    correctionComponentType: CorrectionQuestionType0Component,
    conceptText,
    answer: answerT0,
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
