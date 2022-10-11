import { Component, EventEmitter, Input, OnChanges, OnInit, SimpleChanges, Type, ViewChild } from '@angular/core';
import { QuestionHostDirective } from '../../../utils/question-host.directive';
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

  @Input() set replyExam(val: boolean) {
    if (val) this.replyQuestion();
  }

  @ViewChild(QuestionHostDirective, { static: true }) questionHost!: QuestionHostDirective;

  private componentRef!: any;

  constructor() {}

  ngOnInit(): void {
    this.createDynamicComponent();
  }

  createDynamicComponent() {
    const viewContainerRef = this.questionHost.viewContainerRef;
    viewContainerRef.clear();
    const componentRef = viewContainerRef.createComponent<ExamQuestionComponent>(this.componentType);

    console.log(this.question);

    this.componentRef = componentRef;
    this.componentRef.instance.question = this.question;
    this.componentRef.instance.questionNumber = this.questionNumber;
    componentRef.instance.sendRepliedQuestion.suscribe((val: any) => console.log(val));
  }

  replyQuestion() {
    this.componentRef.instance.replyQuestion = true;
  }
}
