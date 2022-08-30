import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationListComponent } from './knowledge-justification-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeJustificationListFormModule } from '../knowledge-justification-list-form/knowledge-justification-list-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeJustificationListFormModule],
  declarations: [KnowledgeJustificationListComponent],
  exports: [KnowledgeJustificationListComponent],
})
export class KnowledgeJustificationListModule {}
