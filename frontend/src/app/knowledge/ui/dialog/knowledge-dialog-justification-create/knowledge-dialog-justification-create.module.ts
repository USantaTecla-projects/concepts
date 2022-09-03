import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDialogJustificationCreateComponent } from './knowledge-dialog-justification-create.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDialogJustificationCreateComponent],
  exports: [KnowledgeDialogJustificationCreateComponent],
})
export class KnowledgeDialogJustificationCreateModule {}
