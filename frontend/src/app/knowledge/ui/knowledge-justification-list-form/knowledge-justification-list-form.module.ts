import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationListFormComponent } from './knowledge-justification-list-form.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDeleteDialogModule } from '../knowledge-delete-dialog/knowledge-delete-dialog.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDeleteDialogModule],
  declarations: [KnowledgeJustificationListFormComponent],
  exports: [KnowledgeJustificationListFormComponent],
})
export class KnowledgeJustificationListFormModule {}
