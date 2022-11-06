import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorrectionInCourseComponent } from './correction-in-course.component';

const routes: Routes = [
  {
    path: '',
    component: CorrectionInCourseComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CorrectionInCourseRoutingModule {}
