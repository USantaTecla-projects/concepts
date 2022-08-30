import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeConceptListComponent } from './knowledge-concept-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeConceptListFormModule } from '../knowledge-concept-list-form/knowledge-concept-list-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeConceptListFormModule],
  declarations: [KnowledgeConceptListComponent],
  exports: [KnowledgeConceptListComponent],
})
export class KnowledgeConceptListModule {}
