import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { KnowledgeDialogDeleteModule } from 'src/app/knowledge/ui/dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeJustificationUpdateFormComponent } from './knowledge-justification-update-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeJustificationUpdateFormComponent],
  exports: [KnowledgeJustificationUpdateFormComponent],
})
export class KnowledgeJustificationUpdateFormModule {}
