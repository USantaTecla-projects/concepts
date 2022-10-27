import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeConceptListModule } from '../ui/list/knowledge-concept-list/knowledge-concept-list.module';
import { KnowledgeDefinitionListModule } from '../ui/list/knowledge-definition-list/knowledge-definition-list.module';
import { KnowledgeJustificationListModule } from '../ui/list/knowledge-justification-list/knowledge-justification-list.module';
import { KnowledgeManagerRoutingModule } from './knowledge-manager-routing.module';
import { KnowledgeManagerPage } from './knowledge-manager.page';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    KnowledgeManagerRoutingModule,
    KnowledgeConceptListModule,
    KnowledgeDefinitionListModule,
    KnowledgeJustificationListModule,
  ],
  declarations: [KnowledgeManagerPage],
  exports: [],
})
export class KnowledgeManagerPageModule {}
