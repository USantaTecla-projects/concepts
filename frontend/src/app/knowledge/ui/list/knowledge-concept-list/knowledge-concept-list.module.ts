import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';

import { KnowledgeDialogConceptCreateModule } from '../../dialog/knowledge-dialog-concept-create/knowledge-dialog-concept-create.module';
import { KnowledgeConceptUpdateFormModule } from '../../form/knowledge-concept-update-form/knowledge-concept-update-form.module';
import { KnowledgeConceptListComponent } from './knowledge-concept-list.component';

@NgModule({
  imports: [CommonModule, MaterialModule, KnowledgeConceptUpdateFormModule, KnowledgeDialogConceptCreateModule],
  declarations: [KnowledgeConceptListComponent],
  exports: [KnowledgeConceptListComponent],
})
export class KnowledgeConceptListModule {}
