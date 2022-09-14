import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamInitPage } from './exam-init.page';
import { ExamInitRoutingModule } from './exam-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamInitFormModule } from '../../ui/exam-init-form/exam-init-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, ExamInitRoutingModule, ExamInitFormModule],
  declarations: [ExamInitPage],
})
export class ExamInitPageModule {}