import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamInitFormComponent } from './exam-init-form.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ExamInitFormComponent],
  exports: [ExamInitFormComponent],
})
export class ExamInitFormModule {}
