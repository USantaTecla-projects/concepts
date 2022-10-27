import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeConceptCreateFormModule } from '../../form/knowledge-concept-create-form/knowledge-concept-create-form.module';
import { KnowledgeDialogCreateConceptComponent } from './knowledge-dialog-concept-create.component';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeConceptCreateFormModule],
  declarations: [KnowledgeDialogCreateConceptComponent],
  exports: [KnowledgeDialogCreateConceptComponent],
})
export class KnowledgeDialogConceptCreateModule {}
