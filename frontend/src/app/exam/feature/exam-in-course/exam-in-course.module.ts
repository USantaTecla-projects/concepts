import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamInCoursePage } from './exam-in-course.page';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamInCourseRoutingModule } from './exam-in-course.routing.module';
import { ExamQuestionRendererModule } from '../../ui/exam-question-renderer/exam-question-renderer.module';

@NgModule({
  imports: [CommonModule, SharedModule, ExamInCourseRoutingModule, ExamQuestionRendererModule],
  declarations: [ExamInCoursePage],
})
export class ExamInCourseModule {}
