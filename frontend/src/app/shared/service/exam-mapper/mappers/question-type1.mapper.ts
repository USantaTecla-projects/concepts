import { CorrectionQuestionType1Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type1/correction-question-type1.component';

import { ExamQuestionType1Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type1/exam-question-type1.component';
import { QuestionDTO } from 'src/app/shared/types/question/dto/question.dto';
import { QuestionAnswerType } from 'src/app/shared/types/question/enum/question-answer-type.enum';
import { QuestionType1 } from 'src/app/shared/types/question/model/question-type1.model';
import { Question } from 'src/app/shared/types/question/model/question.model';

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
  return <QuestionType1>question;
};
