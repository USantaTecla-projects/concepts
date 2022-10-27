import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeJustificationCreateFormComponent } from './knowledge-justification-create-form.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeJustificationCreateFormComponent],
  exports: [KnowledgeJustificationCreateFormComponent],
})
export class KnowledgeJustificationCreateFormModule {}
