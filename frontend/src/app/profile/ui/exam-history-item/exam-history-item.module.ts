import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamHistoryItemComponent } from './exam-history-item.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ExamHistoryItemComponent],
  exports: [ExamHistoryItemComponent],
  providers: [],
})
export class ExamHistoryItemModule {}
