import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CorrectionPendingListRoutingModule } from './correction-pending-list-routing.module';
import { CorrectionPendingListPage } from './correction-pending-list.page';

@NgModule({
  imports: [CommonModule, CorrectionPendingListRoutingModule, SharedModule, MaterialModule],
  declarations: [CorrectionPendingListPage],
})
export class CorrectionPendingListModule {}
