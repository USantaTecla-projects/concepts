import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamEndComponent } from './exam-end.page';
import { ExamEndRoutingModule } from './exam-end.routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [CommonModule, SharedModule, RouterModule, ExamEndRoutingModule],
  declarations: [ExamEndComponent],
})
export class ExamEndModule {}
