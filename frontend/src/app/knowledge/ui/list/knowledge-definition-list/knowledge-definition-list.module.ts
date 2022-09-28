import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDefinitionListComponent } from './knowledge-definition-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDefinitionUpdateFormModule } from '../../form/knowledge-definition-update-form/knowledge-definition-update-form.module';
import { KnowledgeDialogDefitionCreateModule } from '../../dialog/knowledge-dialog-definition-create/knowledge-dialog-definition-create.module';

@NgModule({
  imports: [CommonModule, SharedModule, KnowledgeDefinitionUpdateFormModule, KnowledgeDialogDefitionCreateModule],
  declarations: [KnowledgeDefinitionListComponent],
  exports: [KnowledgeDefinitionListComponent],
})
export class KnowledgeDefinitionListModule {}
