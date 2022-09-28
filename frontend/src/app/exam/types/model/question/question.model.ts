import { Type } from '@angular/core';
import { ExamQuestionComponent } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question.component';

export interface Question {
  type: Type<ExamQuestionComponent>;
}
