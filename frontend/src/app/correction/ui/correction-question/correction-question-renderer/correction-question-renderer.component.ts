import { Component, Input, OnInit, Type, ViewChild } from '@angular/core';
import { CorrectionQuestionHostDirective } from 'src/app/correction/util/correction-question-host.directive';
import { ExamQuestionComponent } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question.component';

@Component({
  selector: 'app-correction-question-renderer',
  templateUrl: './correction-question-renderer.component.html',
  styleUrls: ['./correction-question-renderer.component.scss'],
})
export class CorrectionQuestionRendererComponent implements OnInit {
  @Input() componentType!: Type<ExamQuestionComponent>;

  @Input() question!: any;

  @Input() questionNumber!: number;

  @Input() userID!: number | undefined;

  @ViewChild(CorrectionQuestionHostDirective, { static: true })
  questionHost!: CorrectionQuestionHostDirective;

  constructor() {}

  ngOnInit(): void {
    this.createDynamicComponent();
  }

  createDynamicComponent() {
    const viewContainerRef = this.questionHost.viewContainerRef;
    viewContainerRef.clear();
    const componentRef = viewContainerRef.createComponent<ExamQuestionComponent>(this.componentType);
    componentRef.instance['question'] = this.question;
    componentRef.instance['questionNumber'] = this.questionNumber;
    componentRef.instance['userID'] = this.userID ?? 0;
  }
}
