import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { CorrectionQuestionComponent } from './correction-question.component';

@NgModule({
  imports: [CommonModule, MaterialModule],
  declarations: [CorrectionQuestionComponent],
  exports: [CorrectionQuestionComponent],
})
export class CorrectionQuestionModule {}
