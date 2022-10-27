import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDialogDefitionCreateModule } from '../../dialog/knowledge-dialog-definition-create/knowledge-dialog-definition-create.module';
import { KnowledgeDefinitionUpdateFormModule } from '../../form/knowledge-definition-update-form/knowledge-definition-update-form.module';
import { KnowledgeDefinitionListComponent } from './knowledge-definition-list.component';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDefinitionUpdateFormModule, KnowledgeDialogDefitionCreateModule],
  declarations: [KnowledgeDefinitionListComponent],
  exports: [KnowledgeDefinitionListComponent],
})
export class KnowledgeDefinitionListModule {}
