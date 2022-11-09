import { CorrectionQuestionType2Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type2/correction-question-type2.component';
import { QuestionDTO } from 'src/app/exam/types/dto/question.dto';
import { QuestionAnswerType } from 'src/app/exam/types/enum/question-answer-type.enum';
import { QuestionType2 } from 'src/app/exam/types/model/question/question-type/question-type2.model';
import { Question } from 'src/app/exam/types/model/question/question.model';
import { ExamQuestionType2Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type2/exam-question-type2.component';

export const mapToQuestionType2 = (questionData: QuestionDTO): QuestionType2 => {
  const { id, conceptID, definitionID, conceptText, definitionText, answerT2 } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE2,
    conceptID,
    definitionID,
    examComponentType: ExamQuestionType2Component,
    correctionComponentType: CorrectionQuestionType2Component,
    conceptText,
    definitionText,
    answer: answerT2,
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
