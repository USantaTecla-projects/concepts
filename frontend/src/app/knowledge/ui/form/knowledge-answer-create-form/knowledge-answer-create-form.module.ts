import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeAnswerCreateFormComponent } from './knowledge-answer-create-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeAnswerCreateFormComponent],
  exports: [KnowledgeAnswerCreateFormComponent],
})
export class KnowledgeAnswerCreateFormModule {}
