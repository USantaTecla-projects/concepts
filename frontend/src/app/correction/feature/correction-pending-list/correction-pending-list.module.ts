import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CorrectionPendingListRoutingModule } from './correction-pending-list-routing.module';
import { CorrectionPendingListComponent } from './correction-pending-list.component';

@NgModule({
  imports: [CommonModule, CorrectionPendingListRoutingModule, SharedModule, MaterialModule],
  declarations: [CorrectionPendingListComponent],
})
export class CorrectionPendingListModule {}
