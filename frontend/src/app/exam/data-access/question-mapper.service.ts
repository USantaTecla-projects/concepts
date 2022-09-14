import { Injectable, Type } from '@angular/core';
import { ExamData } from '../interfaces/dto/exam.dto';
import { Exam } from '../interfaces/model/exam.model';
import { Question } from '../interfaces/model/question.model';
import { ExamQuestionType0Component } from '../ui/question/exam-question-type0/exam-question-type0.component';
import { ExamQuestionType1Component } from '../ui/question/exam-question-type1/exam-question-type1.component';
import { ExamQuestionType2Component } from '../ui/question/exam-question-type2/exam-question-type2.component';
import { ExamQuestionType3Component } from '../ui/question/exam-question-type3/exam-question-type3.component';
import { ExamQuestionComponent } from '../ui/question/exam-question.component';

@Injectable({
  providedIn: 'root',
})
export class QuestionMapperService {
  private componentTypeReference: { [key: string]: Type<ExamQuestionComponent> } = {
    TYPE0: ExamQuestionType0Component,
    TYPE1: ExamQuestionType1Component,
    TYPE2: ExamQuestionType2Component,
    TYPE3: ExamQuestionType3Component,
  };

  constructor() {}

  mapQuestions(examData: ExamData): Exam {
    const { questionList } = examData;

    const mappedQuestionList: Question[] = questionList.map(questionData => {
      const { questionAsString, type } = questionData;

      return {
        questionAsString: questionAsString,
        type: this.componentTypeReference[type],
      };
    });

    return { questionList: mappedQuestionList };
  }
}
