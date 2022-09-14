import { Component, OnInit } from '@angular/core';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type2',
  templateUrl: './exam-question-type2.component.html',
  styleUrls: ['./exam-question-type2.component.scss'],
})
export class ExamQuestionType2Component implements OnInit, ExamQuestionComponent {
  data: any;

  constructor() {}

  ngOnInit(): void {}
}
