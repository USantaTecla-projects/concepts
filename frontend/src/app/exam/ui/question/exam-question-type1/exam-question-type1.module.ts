import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ExamQuestionType1Component } from './exam-question-type1.component';

@NgModule({
  imports: [CommonModule],
  declarations: [ExamQuestionType1Component],
  exports: [ExamQuestionType1Component],
})
export class ExamQuestionType1Module {}
