import { Component, Input, OnInit } from '@angular/core';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type0',
  templateUrl: './exam-question-type0.component.html',
  styleUrls: ['./exam-question-type0.component.scss'],
})
export class ExamQuestionType0Component implements OnInit, ExamQuestionComponent {
  @Input()
  data: any;

  constructor() {}

  ngOnInit(): void {}
}
