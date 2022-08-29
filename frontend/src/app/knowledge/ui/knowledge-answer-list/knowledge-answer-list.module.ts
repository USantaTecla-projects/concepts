import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeAnswerListComponent } from './knowledge-answer-list.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeAnswerListComponent],
  exports: [KnowledgeAnswerListComponent],
})
export class KnowledgeAnswerListModule {}
