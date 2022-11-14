import { CorrectionQuestionType0Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type0/correction-question-type0.component';
import { ExamQuestionType0Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type0/exam-question-type0.component';
import { QuestionType0 } from 'src/app/shared/types/question/model/question-type0.model';
import { QuestionDTO } from '../../../shared/types/question/dto/question.dto';
import { QuestionAnswerType } from '../../../shared/types/question/enum/question-answer-type.enum';
import { Question } from '../../../shared/types/question/model/question.model';

export const mapToQuestionType0 = (questionDTO: QuestionDTO): QuestionType0 => {
  const { id, conceptID, conceptText, answerT0 } = questionDTO;
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
  return <QuestionType0>question;
};
