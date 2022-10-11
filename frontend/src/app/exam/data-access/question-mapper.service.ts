import { Injectable } from '@angular/core';
import { ExamData } from '../types/dto/exam.dto';
import { Exam } from '../types/model/exam.model';
import { QuestionTypeX } from '../types/question-typeX.type';
import { questionTypeMappers } from '../utils/mappers.util';
import { questionTypeRepliers } from '../utils/rreplier.util';

@Injectable({
  providedIn: 'root',
})
export class QuestionMapperService {
  constructor() {}

  mapQuestions(examData: ExamData): Exam {
    const { id, userID, creationDate, questionDataList } = examData;

    const mappedList: QuestionTypeX[] = questionDataList.map(questionData => {
      const { type } = questionData;
      return questionTypeMappers[type](questionData);
    });

    return { id, userID, creationDate, questionList: mappedList };
  }
}
