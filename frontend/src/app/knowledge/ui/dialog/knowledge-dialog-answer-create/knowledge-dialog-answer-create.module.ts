import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDialogAnswerCreateComponent } from './knowledge-dialog-answer-create.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDialogAnswerCreateComponent],
  exports: [KnowledgeDialogAnswerCreateComponent],
})
export class KnowledgeDialogAnswerCreateModule {}
