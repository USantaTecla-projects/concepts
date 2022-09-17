import { Component, Input, OnInit } from '@angular/core';
import { QuestionType1 } from 'src/app/exam/types/model/question/question-type/question-type1.dto';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type1',
  templateUrl: './exam-question-type1.component.html',
  styleUrls: ['./exam-question-type1.component.scss'],
})
export class ExamQuestionType1Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType1;

  constructor() {}

  ngOnInit(): void {}
}
