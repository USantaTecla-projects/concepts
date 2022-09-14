import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamQuestionType3Component } from './exam-question-type3.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ExamQuestionType3Component],
  exports: [ExamQuestionType3Component],
})
export class ExamQuestionType3Module {}
