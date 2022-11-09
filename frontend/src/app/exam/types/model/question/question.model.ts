import { Type } from '@angular/core';
import { CorrectionQuestionComponent } from 'src/app/correction/ui/correction-question/correction-question-types/correction-question.component';
import { ExamQuestionComponent } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question.component';
import { QuestionAnswerType } from '../../enum/question-answer-type.enum';

export interface Question {
  id: number;
  type: QuestionAnswerType;
  examComponentType: Type<ExamQuestionComponent>;
  correctionComponentType: Type<CorrectionQuestionComponent>;
  [x: string]: any;
}
