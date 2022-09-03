import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationListComponent } from './knowledge-justification-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeJustificationUpdateFormModule } from '../../form/knowledge-justification-update-form/knowledge-justification-update-form.module';
import { KnowledgeDialogJustificationCreateModule } from '../../dialog/knowledge-dialog-justification-create/knowledge-dialog-justification-create.module';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    KnowledgeJustificationUpdateFormModule,
    KnowledgeDialogJustificationCreateModule,
  ],
  declarations: [KnowledgeJustificationListComponent],
  exports: [KnowledgeJustificationListComponent],
})
export class KnowledgeJustificationListModule {}
