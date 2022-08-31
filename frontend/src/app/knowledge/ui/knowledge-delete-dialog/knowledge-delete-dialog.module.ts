import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDeleteDialogComponent } from './knowledge-delete-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDeleteDialogComponent],
  exports: [KnowledgeDeleteDialogComponent],
})
export class KnowledgeDeleteDialogModule {}
