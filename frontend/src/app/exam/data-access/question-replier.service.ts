import { Injectable } from '@angular/core';
import { QuestionAnswerType } from '../types/enums/question-answer-type.enum';
import { Question } from '../types/model/question/question.model';
import { questionTypeRepliers } from '../utils/rreplier.util';

@Injectable({
  providedIn: 'root',
})
export class QuestionReplierService {
  constructor() {}

  storeAnswerOnQuestion(userID: number, question: Question, reply: any): Question {
    const { type } = question;
    console.log(questionTypeRepliers[question.type]);
    return questionTypeRepliers[type](userID, question, reply);
  }
}
