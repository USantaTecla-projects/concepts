import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamInitFormComponent } from './exam-init-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule],
  declarations: [ExamInitFormComponent],
  exports: [ExamInitFormComponent],
})
export class ExamInitFormModule {}
