import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamEndComponent } from './exam-end.page';
import { ExamEndRoutingModule } from './exam-end.routing.module';

@NgModule({
  imports: [CommonModule, SharedModule, RouterModule, ExamEndRoutingModule],
  declarations: [ExamEndComponent],
})
export class ExamEndModule {}
