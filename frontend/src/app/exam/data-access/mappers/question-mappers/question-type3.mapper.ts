import { CorrectionQuestionType3Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type3/correction-question-type3.component';
import { QuestionDTO } from 'src/app/exam/types/dto/question.dto';
import { QuestionAnswerType } from 'src/app/exam/types/enum/question-answer-type.enum';
import { QuestionType3 } from 'src/app/exam/types/model/question/question-type/question-type3.model';
import { Question } from 'src/app/exam/types/model/question/question.model';
import { ExamQuestionType3Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type3/exam-question-type3.component';

export const mapToQuestionType3 = (questionData: QuestionDTO): QuestionType3 => {
  const {
    id,
    conceptID,
    definitionID,
    justificationID,
    conceptText,
    incorrectDefinitionText,
    justificationText,
    answerT3,
  } = questionData;
  return {
    id,
    type: QuestionAnswerType.TYPE3,
    conceptID,
    definitionID,
    justificationID,
    examComponentType: ExamQuestionType3Component,
    correctionComponentType: CorrectionQuestionType3Component,
    conceptText,
    incorrectDefinitionText,
    justificationText,
    answer: answerT3,
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
