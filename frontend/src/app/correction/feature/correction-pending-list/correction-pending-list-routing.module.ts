import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorrectionPendingListComponent } from './correction-pending-list.component';

const routes: Routes = [
  {
    path: '',
    component: CorrectionPendingListComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CorrectionPendingListRoutingModule {}
