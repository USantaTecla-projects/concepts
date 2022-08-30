import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeAnswerListFormComponent } from './knowledge-answer-list-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeAnswerListFormComponent],
  exports: [KnowledgeAnswerListFormComponent],
})
export class KnowledgeAnswerListFormModule {}
