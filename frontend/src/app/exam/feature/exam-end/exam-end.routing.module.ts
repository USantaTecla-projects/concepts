import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExamEndComponent } from './exam-end.page';

const routes: Routes = [
  {
    path: '',
    component: ExamEndComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExamEndRoutingModule {}
