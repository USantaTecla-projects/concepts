import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeConceptUpdateFormComponent } from './knowledge-concept-update-form.component';
import { KnowledgeDialogDeleteModule } from '../../../ui/dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeConceptUpdateFormComponent],
  exports: [KnowledgeConceptUpdateFormComponent],
})
export class KnowledgeConceptUpdateFormModule {}
