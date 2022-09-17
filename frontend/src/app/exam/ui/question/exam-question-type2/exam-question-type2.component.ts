import { Component, Input, OnInit } from '@angular/core';
import { QuestionType2 } from 'src/app/exam/types/model/question/question-type/question-type2.dto ';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type2',
  templateUrl: './exam-question-type2.component.html',
  styleUrls: ['./exam-question-type2.component.scss'],
})
export class ExamQuestionType2Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType2;

  constructor() {}

  ngOnInit(): void {}
}
