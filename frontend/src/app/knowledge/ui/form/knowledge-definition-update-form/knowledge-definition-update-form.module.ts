import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { KnowledgeDialogDeleteModule } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';
import { KnowledgeDefinitionUpdateFormComponent } from './knowledge-definition-update-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeDefinitionUpdateFormComponent],
  exports: [KnowledgeDefinitionUpdateFormComponent],
})
export class KnowledgeDefinitionUpdateFormModule {}
