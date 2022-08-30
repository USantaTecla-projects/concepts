import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeConceptListFormComponent } from './knowledge-concept-list-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeConceptListFormComponent],
  exports: [KnowledgeConceptListFormComponent],
})
export class KnowledgeConceptListFormModule {}
