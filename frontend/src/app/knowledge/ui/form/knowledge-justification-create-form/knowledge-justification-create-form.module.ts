import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationCreateFormComponent } from './knowledge-justification-create-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeJustificationCreateFormComponent],
  exports: [KnowledgeJustificationCreateFormComponent],
})
export class KnowledgeJustificationCreateFormModule {}
