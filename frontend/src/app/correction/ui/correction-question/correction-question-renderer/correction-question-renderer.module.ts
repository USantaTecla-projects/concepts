import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CorrectionQuestionHostDirective } from 'src/app/correction/util/correction-question-host.directive';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CorrectionQuestionType0Component } from '../correction-question-types/correction-question-type0/correction-question-type0.component';
import { CorrectionQuestionType1Component } from '../correction-question-types/correction-question-type1/correction-question-type1.component';
import { CorrectionQuestionType2Component } from '../correction-question-types/correction-question-type2/correction-question-type2.component';
import { CorrectionQuestionType3Component } from '../correction-question-types/correction-question-type3/correction-question-type3.component';

import { CorrectionQuestionRendererComponent } from './correction-question-renderer.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule, ReactiveFormsModule],
  declarations: [
    CorrectionQuestionHostDirective,
    CorrectionQuestionRendererComponent,
    CorrectionQuestionType0Component,
    CorrectionQuestionType1Component,
    CorrectionQuestionType2Component,
    CorrectionQuestionType3Component,
  ],
  exports: [CorrectionQuestionRendererComponent],
})
export class CorrectionQuestionRendererModule {}
