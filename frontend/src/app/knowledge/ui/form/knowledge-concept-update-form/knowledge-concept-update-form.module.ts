import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { KnowledgeDialogDeleteModule } from '../../../ui/dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';
import { KnowledgeConceptUpdateFormComponent } from './knowledge-concept-update-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeConceptUpdateFormComponent],
  exports: [KnowledgeConceptUpdateFormComponent],
})
export class KnowledgeConceptUpdateFormModule {}
