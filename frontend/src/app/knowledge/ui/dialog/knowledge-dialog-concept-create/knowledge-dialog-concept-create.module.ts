import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogCreateConceptComponent } from './knowledge-dialog-concept-create.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDialogCreateConceptComponent],
  exports: [KnowledgeDialogCreateConceptComponent],
})
export class KnowledgeDialogConceptCreateModule {}
