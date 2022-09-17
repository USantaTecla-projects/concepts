import { Component, Input, OnInit } from '@angular/core';
import { QuestionType0 } from 'src/app/exam/types/model/question/question-type/question-type0.dto';
import { ExamQuestionComponent } from '../exam-question.component';

@Component({
  selector: 'app-exam-question-type0',
  templateUrl: './exam-question-type0.component.html',
  styleUrls: ['./exam-question-type0.component.scss'],
})
export class ExamQuestionType0Component implements OnInit, ExamQuestionComponent {
  @Input() question!: QuestionType0;

  constructor() {}

  ngOnInit(): void {}
}
