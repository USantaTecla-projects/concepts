import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { KnowledgeDefinitionCreateFormComponent } from './knowledge-definition-create-form.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDefinitionCreateFormComponent],
  exports: [KnowledgeDefinitionCreateFormComponent],
})
export class KnowledgeDefinitionCreateFormModule {}
