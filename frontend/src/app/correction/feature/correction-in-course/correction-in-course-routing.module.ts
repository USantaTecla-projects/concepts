import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorrectionInCoursePage } from './correction-in-course.page';

const routes: Routes = [
  {
    path: '',
    component: CorrectionInCoursePage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CorrectionInCourseRoutingModule {}
