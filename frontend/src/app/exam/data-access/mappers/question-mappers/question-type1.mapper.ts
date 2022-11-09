import { CorrectionQuestionType1Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type1/correction-question-type1.component';
import { QuestionDTO } from 'src/app/exam/types/dto/question.dto';
import { QuestionAnswerType } from 'src/app/exam/types/enum/question-answer-type.enum';
import { QuestionType1 } from 'src/app/exam/types/model/question/question-type/question-type1.model';
import { Question } from 'src/app/exam/types/model/question/question.model';
import { ExamQuestionType1Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type1/exam-question-type1.component';

export const mapToQuestionType1 = (questionData: QuestionDTO): QuestionType1 => {
  const { id, conceptID, definitionID, conceptText, incorrectDefinitionText, answerT1 } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE1,
    conceptID,
    definitionID,
    examComponentType: ExamQuestionType1Component,
    correctionComponentType: CorrectionQuestionType1Component,
    conceptText,
    incorrectDefinitionText,
    answer: answerT1,
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
