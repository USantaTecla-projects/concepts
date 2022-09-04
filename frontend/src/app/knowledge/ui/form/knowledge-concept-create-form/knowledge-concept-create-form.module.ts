import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeConceptCreateFormComponent } from './knowledge-concept-create-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeConceptCreateFormComponent],
  exports: [KnowledgeConceptCreateFormComponent],
})
export class KnowledgeConceptCreateFormModule {}
