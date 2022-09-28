import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KnowledgeDefinitionCreateFormComponent } from './knowledge-definition-create-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [KnowledgeDefinitionCreateFormComponent],
  exports: [KnowledgeDefinitionCreateFormComponent],
})
export class KnowledgeDefinitionCreateFormModule {}
