import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeAnswerListComponent } from './knowledge-answer-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeAnswerListFormModule } from '../knowledge-answer-list-form/knowledge-answer-list-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeAnswerListFormModule],
  declarations: [KnowledgeAnswerListComponent],
  exports: [KnowledgeAnswerListComponent],
})
export class KnowledgeAnswerListModule {}
