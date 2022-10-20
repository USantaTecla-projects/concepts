import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('../correction-pending-list/correction-pending-list.module').then(m => m.CorrectionPendingListModule),
  },
  {
    path: '/:id',
    loadChildren: () =>
      import('../correction-in-course/correction-in-course.module').then(m => m.CorrectionInCourseModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CorrectionShellRoutingModule {}
