import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDialogDefinitionCreateComponent } from './knowledge-dialog-definition-create.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDefinitionCreateFormModule } from '../../form/knowledge-definition-create-form/knowledge-definition-create-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDefinitionCreateFormModule],
  declarations: [KnowledgeDialogDefinitionCreateComponent],
  exports: [KnowledgeDialogDefinitionCreateComponent],
})
export class KnowledgeDialogDefitionCreateModule {}
