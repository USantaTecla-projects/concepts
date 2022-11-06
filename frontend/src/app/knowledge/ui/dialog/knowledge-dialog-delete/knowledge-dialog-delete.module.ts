import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogDeleteComponent } from './knowledge-dialog-delete.component';

@NgModule({
  imports: [CommonModule, SharedModule, MaterialModule],
  declarations: [KnowledgeDialogDeleteComponent],
  exports: [KnowledgeDialogDeleteComponent],
})
export class KnowledgeDialogDeleteModule {}
