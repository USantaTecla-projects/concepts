import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationListComponent } from './knowledge-justification-list.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeJustificationListComponent],
  exports: [KnowledgeJustificationListComponent],
})
export class KnowledgeJustificationListModule {}
