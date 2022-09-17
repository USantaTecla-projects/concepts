import { Type } from '@angular/core';
import { ExamQuestionComponent } from 'src/app/exam/ui/question/exam-question.component';

export interface Question {
  type: Type<ExamQuestionComponent>;
}
