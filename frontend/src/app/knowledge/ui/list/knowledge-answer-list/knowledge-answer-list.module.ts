import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeAnswerListComponent } from './knowledge-answer-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeAnswerUpdateFormModule } from '../../form/knowledge-answer-update-form/knowledge-answer-update-form.module';
import { KnowledgeDialogAnswerCreateModule } from '../../dialog/knowledge-dialog-answer-create/knowledge-dialog-answer-create.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeAnswerUpdateFormModule, KnowledgeDialogAnswerCreateModule],
  declarations: [KnowledgeAnswerListComponent],
  exports: [KnowledgeAnswerListComponent],
})
export class KnowledgeAnswerListModule {}
