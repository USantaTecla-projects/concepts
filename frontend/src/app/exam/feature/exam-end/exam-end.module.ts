import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/shared/material.module';
import { ExamEndPage } from './exam-end.page';
import { ExamEndRoutingModule } from './exam-end.routing.module';

@NgModule({
  imports: [CommonModule, MaterialModule, RouterModule, ExamEndRoutingModule],
  declarations: [ExamEndPage],
})
export class ExamEndModule {}
