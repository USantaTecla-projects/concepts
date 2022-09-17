import { Type } from '@angular/core';
import { ExamQuestionComponent } from 'src/app/exam/ui/question/exam-question.component';
import { Question } from '../question.model';

export interface QuestionType2 extends Question {
  conceptText: string;
  answerText: string;
}
