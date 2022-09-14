import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'init',
    loadChildren: () => import('../exam-init/exam-init.module').then(m => m.ExamInitPageModule),
  },
  {
    path: 'in-course',
    loadChildren: () => import('../exam-in-course/exam-in-course.module').then(m => m.ExamInCourseModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExamShellRoutingModule {}
