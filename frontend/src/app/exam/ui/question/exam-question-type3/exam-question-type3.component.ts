import { Component, Input, OnInit } from '@angular/core';
import { QuestionType3 } from 'src/app/exam/types/model/question/question-type/question-type3.dto';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type3',
  templateUrl: './exam-question-type3.component.html',
  styleUrls: ['./exam-question-type3.component.scss'],
})
export class ExamQuestionType3Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType3;

  constructor() {}

  ngOnInit(): void {}
}
