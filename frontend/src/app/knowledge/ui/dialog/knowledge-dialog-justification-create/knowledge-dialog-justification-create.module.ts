import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDialogJustificationCreateComponent } from './knowledge-dialog-justification-create.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeJustificationCreateFormModule } from '../../form/knowledge-justification-create-form/knowledge-justification-create-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeJustificationCreateFormModule],
  declarations: [KnowledgeDialogJustificationCreateComponent],
  exports: [KnowledgeDialogJustificationCreateComponent],
})
export class KnowledgeDialogJustificationCreateModule {}
