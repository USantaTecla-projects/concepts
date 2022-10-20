import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CorrectionPendingListComponent } from './correction-pending-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { CorrectionPendingListRoutingModule } from './correction-pending-list-routing.module';

@NgModule({
  imports: [CommonModule, CorrectionPendingListRoutingModule, SharedModule],
  declarations: [CorrectionPendingListComponent],
})
export class CorrectionPendingListModule {}
