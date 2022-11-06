import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeJustificationCreateFormComponent } from './knowledge-justification-create-form.component';

@NgModule({
  imports: [CommonModule, MaterialModule, SharedModule],
  declarations: [KnowledgeJustificationCreateFormComponent],
  exports: [KnowledgeJustificationCreateFormComponent],
})
export class KnowledgeJustificationCreateFormModule {}
