import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExamInitPage } from './exam-init.page';

const routes: Routes = [
  {
    path: '',
    component: ExamInitPage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExamInitRoutingModule {}
