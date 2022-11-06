import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/shared/material.module';
import { ExamEndComponent } from './exam-end.page';
import { ExamEndRoutingModule } from './exam-end.routing.module';

@NgModule({
  imports: [CommonModule, MaterialModule, RouterModule, ExamEndRoutingModule],
  declarations: [ExamEndComponent],
})
export class ExamEndModule {}
