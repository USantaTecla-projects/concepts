import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeConceptListComponent } from './knowledge-concept-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeConceptUpdateFormModule } from '../../form/knowledge-concept-update-form/knowledge-concept-update-form.module';
import { KnowledgeDialogConceptCreateModule } from '../../dialog/knowledge-dialog-concept-create/knowledge-dialog-concept-create.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeConceptUpdateFormModule, KnowledgeDialogConceptCreateModule],
  declarations: [KnowledgeConceptListComponent],
  exports: [KnowledgeConceptListComponent],
})
export class KnowledgeConceptListModule {}
