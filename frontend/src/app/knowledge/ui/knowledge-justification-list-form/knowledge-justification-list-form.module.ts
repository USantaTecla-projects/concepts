import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeJustificationListFormComponent } from './knowledge-justification-list-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeJustificationListFormComponent],
  exports: [KnowledgeJustificationListFormComponent],
})
export class KnowledgeJustificationListFormModule {}
