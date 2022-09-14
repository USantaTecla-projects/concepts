import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExamInCoursePage } from './exam-in-course.page';

const routes: Routes = [
  {
    path: '',
    component: ExamInCoursePage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExamInCourseRoutingModule {}
