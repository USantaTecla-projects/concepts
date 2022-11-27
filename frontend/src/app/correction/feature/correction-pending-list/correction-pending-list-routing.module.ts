import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorrectionPendingListPage } from './correction-pending-list.page';

const routes: Routes = [
  {
    path: '',
    component: CorrectionPendingListPage,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CorrectionPendingListRoutingModule {}
