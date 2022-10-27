import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { SharedModule } from 'src/app/shared/shared.module';
import { ExamHistoryItemModule } from '../ui/exam-history-item/exam-history-item.module';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfilePage } from './profile.page';

@NgModule({
  imports: [CommonModule, ProfileRoutingModule, SharedModule, ExamHistoryItemModule],
  declarations: [ProfilePage],
})
export class ProfileModule {}
