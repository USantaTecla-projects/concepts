import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDialogAnswerCreateComponent } from './knowledge-dialog-answer-create.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeAnswerCreateFormModule } from '../../form/knowledge-answer-create-form/knowledge-answer-create-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeAnswerCreateFormModule],
  declarations: [KnowledgeDialogAnswerCreateComponent],
  exports: [KnowledgeDialogAnswerCreateComponent],
})
export class KnowledgeDialogAnswerCreateModule {}
