import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamShellRoutingModule } from './exam-shell-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule, ExamShellRoutingModule],
  declarations: [],
})
export class ExamShellModule {}
