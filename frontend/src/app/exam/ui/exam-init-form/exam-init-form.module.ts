import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamInitFormComponent } from './exam-init-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ExamInitFormComponent],
  exports: [ExamInitFormComponent],
})
export class ExamInitFormModule {}
