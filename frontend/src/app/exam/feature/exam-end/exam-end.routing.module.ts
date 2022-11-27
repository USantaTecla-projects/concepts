import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExamEndPage } from './exam-end.page';

const routes: Routes = [
  {
    path: '',
    component: ExamEndPage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExamEndRoutingModule {}
