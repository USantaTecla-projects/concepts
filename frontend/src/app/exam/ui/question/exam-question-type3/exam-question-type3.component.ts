import { Component, OnInit } from '@angular/core';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type3',
  templateUrl: './exam-question-type3.component.html',
  styleUrls: ['./exam-question-type3.component.scss'],
})
export class ExamQuestionType3Component implements OnInit, ExamQuestionComponent {
  data: any;

  constructor() {}

  ngOnInit(): void {}
}
