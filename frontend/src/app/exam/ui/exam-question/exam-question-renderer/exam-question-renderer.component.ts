import { Component, Input, OnInit, Type, ViewChild } from '@angular/core';
import { QuestionHostDirective } from 'src/app/exam/utils/question-host.directive';
import { ExamQuestionComponent } from '../exam-question-types/exam-question.component';

@Component({
  selector: 'app-exam-question-renderer',
  templateUrl: './exam-question-renderer.component.html',
  styleUrls: ['./exam-question-renderer.component.scss'],
})
export class ExamQuestionRendererComponent implements OnInit {
  @Input() componentType!: Type<ExamQuestionComponent>;

  @Input() question!: any;

  @Input() questionNumber!: number;

  @Input() userID!: number;

  @ViewChild(QuestionHostDirective, { static: true })
  questionHost!: QuestionHostDirective;

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
    componentRef.instance['userID'] = this.userID;
  }
}
