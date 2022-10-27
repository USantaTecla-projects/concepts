import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamInitFormModule } from '../../ui/exam-init-form/exam-init-form.module';
import { ExamInitPage } from './exam-init.page';
import { ExamInitRoutingModule } from './exam-routing.module';

@NgModule({
  imports: [CommonModule, SharedModule, ExamInitRoutingModule, ExamInitFormModule],
  declarations: [ExamInitPage],
})
export class ExamInitPageModule {}
