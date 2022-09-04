import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogCreateConceptComponent } from './knowledge-dialog-concept-create.component';
import { KnowledgeConceptCreateFormModule } from '../../form/knowledge-concept-create-form/knowledge-concept-create-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeConceptCreateFormModule],
  declarations: [KnowledgeDialogCreateConceptComponent],
  exports: [KnowledgeDialogCreateConceptComponent],
})
export class KnowledgeDialogConceptCreateModule {}
