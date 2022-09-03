import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationUpdateFormComponent } from './knowledge-justification-update-form.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogDeleteModule } from 'src/app/knowledge/ui/dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeJustificationUpdateFormComponent],
  exports: [KnowledgeJustificationUpdateFormComponent],
})
export class KnowledgeJustificationUpdateFormModule {}
