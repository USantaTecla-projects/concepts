import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { QuestionHostDirective } from '../../../utils/question-host.directive';

import { ExamQuestionType0Component } from '../exam-question-types/exam-question-type0/exam-question-type0.component';
import { ExamQuestionType1Component } from '../exam-question-types/exam-question-type1/exam-question-type1.component';
import { ExamQuestionType2Component } from '../exam-question-types/exam-question-type2/exam-question-type2.component';
import { ExamQuestionType3Component } from '../exam-question-types/exam-question-type3/exam-question-type3.component';
import { ExamQuestionRendererComponent } from './exam-question-renderer.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [
    ExamQuestionRendererComponent,
    QuestionHostDirective,
    ExamQuestionType0Component,
    ExamQuestionType1Component,
    ExamQuestionType2Component,
    ExamQuestionType3Component,
  ],
  exports: [ExamQuestionRendererComponent],
})
export class ExamQuestionRendererModule {}
