import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamQuestionType2Component } from './exam-question-type2.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ExamQuestionType2Component],
  exports: [ExamQuestionType2Component],
})
export class ExamQuestionType2Module {}
