import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogJustificationCreateModule } from '../../dialog/knowledge-dialog-justification-create/knowledge-dialog-justification-create.module';
import { KnowledgeJustificationUpdateFormModule } from '../../form/knowledge-justification-update-form/knowledge-justification-update-form.module';
import { KnowledgeJustificationListComponent } from './knowledge-justification-list.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    SharedModule,
    KnowledgeJustificationUpdateFormModule,
    KnowledgeDialogJustificationCreateModule,
  ],
  declarations: [KnowledgeJustificationListComponent],
  exports: [KnowledgeJustificationListComponent],
})
export class KnowledgeJustificationListModule {}
