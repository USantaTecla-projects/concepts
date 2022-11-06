import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeConceptCreateFormComponent } from './knowledge-concept-create-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule],
  declarations: [KnowledgeConceptCreateFormComponent],
  exports: [KnowledgeConceptCreateFormComponent],
})
export class KnowledgeConceptCreateFormModule {}
