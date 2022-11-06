import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { ExamInitFormModule } from '../../ui/exam-init-form/exam-init-form.module';
import { ExamInitPage } from './exam-init.page';
import { ExamInitRoutingModule } from './exam-routing.module';

@NgModule({
  imports: [CommonModule, MaterialModule, ExamInitRoutingModule, ExamInitFormModule],
  declarations: [ExamInitPage],
})
export class ExamInitPageModule {}
