import { CorrectionQuestionType3Component } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question-type3/correction-question-type3.component';

import { ExamQuestionType3Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type3/exam-question-type3.component';
import { QuestionDTO } from 'src/app/shared/types/question/dto/question.dto';
import { QuestionAnswerType } from 'src/app/shared/types/question/enum/question-answer-type.enum';
import { QuestionType3 } from 'src/app/shared/types/question/model/question-type3.model';
import { Question } from 'src/app/shared/types/question/model/question.model';

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
  return <QuestionType3>question;
};
