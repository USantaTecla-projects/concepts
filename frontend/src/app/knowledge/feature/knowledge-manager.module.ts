import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeManagerRoutingModule } from './knowledge-manager-routing.module';
import { KnowledgeManagerPage } from './knowledge-manager.page';
import { KnowledgeAnswerListModule } from '../ui/list/knowledge-answer-list/knowledge-answer-list.module';
import { KnowledgeConceptListModule } from '../ui/list/knowledge-concept-list/knowledge-concept-list.module';
import { KnowledgeJustificationListModule } from '../ui/list/knowledge-justification-list/knowledge-justification-list.module';

@NgModule({
  declarations: [KnowledgeManagerPage],
  imports: [
    CommonModule,
    KnowledgeManagerRoutingModule,
    KnowledgeConceptListModule,
    KnowledgeAnswerListModule,
    KnowledgeJustificationListModule,
  ],
  exports: [],
})
export class KnowledgeManagerPageModule {}
