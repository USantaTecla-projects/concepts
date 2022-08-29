import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KnowledgeManagerRoutingModule } from './knowledge-manager-routing.module';
import { KnowledgeManagerPage } from './knowledge-manager.page';
import { KnowledgeConceptListModule } from '../ui/knowledge-concept-list/knowledge-concept-list.module';
import { KnowledgeAnswerListModule } from '../ui/knowledge-answer-list/knowledge-answer-list.module';
import { KnowledgeJustificationListModule } from '../ui/knowledge-justification-list/knowledge-justification-list.module';

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
