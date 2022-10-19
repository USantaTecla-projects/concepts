import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfilePage } from './profile.page';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExamHistoryItemModule } from '../ui/exam-history-item/exam-history-item.module';
import { TimeSpentPipe } from '../../shared/utils/time-spent.pipe';

@NgModule({
  imports: [CommonModule, ProfileRoutingModule, SharedModule, ExamHistoryItemModule],
  declarations: [ProfilePage],
})
export class ProfileModule {}
