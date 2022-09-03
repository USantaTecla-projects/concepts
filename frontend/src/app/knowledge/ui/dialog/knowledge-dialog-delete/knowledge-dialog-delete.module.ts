import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDialogDeleteComponent } from './knowledge-dialog-delete.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDialogDeleteComponent],
  exports: [KnowledgeDialogDeleteComponent],
})
export class KnowledgeDialogDeleteModule {}
