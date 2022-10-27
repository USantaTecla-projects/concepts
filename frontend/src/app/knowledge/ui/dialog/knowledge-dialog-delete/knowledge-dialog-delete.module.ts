import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogDeleteComponent } from './knowledge-dialog-delete.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDialogDeleteComponent],
  exports: [KnowledgeDialogDeleteComponent],
})
export class KnowledgeDialogDeleteModule {}
