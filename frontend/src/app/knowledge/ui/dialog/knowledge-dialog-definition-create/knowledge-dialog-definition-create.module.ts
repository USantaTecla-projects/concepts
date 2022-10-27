import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDefinitionCreateFormModule } from '../../form/knowledge-definition-create-form/knowledge-definition-create-form.module';
import { KnowledgeDialogDefinitionCreateComponent } from './knowledge-dialog-definition-create.component';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDefinitionCreateFormModule],
  declarations: [KnowledgeDialogDefinitionCreateComponent],
  exports: [KnowledgeDialogDefinitionCreateComponent],
})
export class KnowledgeDialogDefitionCreateModule {}
