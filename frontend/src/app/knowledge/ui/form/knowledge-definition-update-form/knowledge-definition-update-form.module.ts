import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeDefinitionUpdateFormComponent } from './knowledge-definition-update-form.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogDeleteModule } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeDefinitionUpdateFormComponent],
  exports: [KnowledgeDefinitionUpdateFormComponent],
})
export class KnowledgeDefinitionUpdateFormModule {}
