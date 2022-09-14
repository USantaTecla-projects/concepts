import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExamQuestionRendererComponent } from './exam-question-renderer.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { QuestionHostDirective } from '../../utils/question-host.directive';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ExamQuestionRendererComponent, QuestionHostDirective],
  exports: [ExamQuestionRendererComponent],
})
export class ExamQuestionRendererModule {}
