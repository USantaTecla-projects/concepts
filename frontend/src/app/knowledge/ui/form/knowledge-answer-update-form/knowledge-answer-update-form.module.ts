import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeAnswerUpdateFormComponent } from './knowledge-answer-update-form.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogDeleteModule } from '../../dialog/knowledge-dialog-delete/knowledge-dialog-delete.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDialogDeleteModule],
  declarations: [KnowledgeAnswerUpdateFormComponent],
  exports: [KnowledgeAnswerUpdateFormComponent],
})
export class KnowledgeAnswerUpdateFormModule {}
