import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { CorrectionQuestionRendererModule } from '../../ui/correction-question/correction-question-renderer/correction-question-renderer.module';
import { CorrectionInCourseRoutingModule } from './correction-in-course-routing.module';
import { CorrectionInCoursePage } from './correction-in-course.page';

@NgModule({
  imports: [CommonModule, MaterialModule, CorrectionInCourseRoutingModule, CorrectionQuestionRendererModule],

  declarations: [CorrectionInCoursePage],
})
export class CorrectionInCourseModule {}
