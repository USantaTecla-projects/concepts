import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { ExamQuestionRendererModule } from '../../ui/exam-question/exam-question-renderer/exam-question-renderer.module';
import { ExamInCoursePage } from './exam-in-course.page';
import { ExamInCourseRoutingModule } from './exam-in-course.routing.module';

@NgModule({
  imports: [CommonModule, MaterialModule, ExamInCourseRoutingModule, ExamQuestionRendererModule],
  declarations: [ExamInCoursePage],
})
export class ExamInCourseModule {}
