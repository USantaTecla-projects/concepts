import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamEndComponent } from './exam-end.page';
import { ExamEndRoutingModule } from './exam-end.routing.module';

@NgModule({
  imports: [CommonModule, ExamEndRoutingModule],
  declarations: [ExamEndComponent],
})
export class ExamEndModule {}
