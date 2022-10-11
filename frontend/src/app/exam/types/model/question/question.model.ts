import { Type } from '@angular/core';
import { ExamQuestionComponent } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question.component';
import { QuestionAnswerType } from '../../enums/question-answer-type.enum';

export interface Question {
  id: number;
  type: QuestionAnswerType;
  componentType: Type<ExamQuestionComponent>;
}
