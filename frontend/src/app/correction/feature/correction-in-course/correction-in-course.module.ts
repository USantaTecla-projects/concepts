import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { CorrectionQuestionModule } from '../../ui/correction-question/correction-question.module';
import { CorrectionInCourseRoutingModule } from './correction-in-course-routing.module';
import { CorrectionInCourseComponent } from './correction-in-course.component';

@NgModule({
  imports: [CommonModule, MaterialModule, CorrectionInCourseRoutingModule, CorrectionQuestionModule],
  declarations: [CorrectionInCourseComponent],
})
export class CorrectionInCourseModule {}
