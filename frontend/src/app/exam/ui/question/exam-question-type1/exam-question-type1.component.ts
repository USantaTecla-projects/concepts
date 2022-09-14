import { Component, Input, OnInit } from '@angular/core';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type1',
  templateUrl: './exam-question-type1.component.html',
  styleUrls: ['./exam-question-type1.component.scss'],
})
export class ExamQuestionType1Component implements OnInit, ExamQuestionComponent {
  @Input()
  data: any;

  constructor() {}

  ngOnInit(): void {}
}
