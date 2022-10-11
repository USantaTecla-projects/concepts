import { QuestionData } from '../../types/dto/question.dto';
import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType0 } from '../../types/model/question/question-type/question-type0.model';
import { ExamQuestionType0Component } from '../../ui/exam-question/exam-question-types/exam-question-type0/exam-question-type0.component';

export const mapQuestionType0 = (questionData: QuestionData): QuestionType0 => {
  const { id, conceptID, conceptText } = questionData;

  console.log(conceptText);

  return { id, type: QuestionAnswerType.TYPE0, conceptID, componentType: ExamQuestionType0Component, conceptText };
};
