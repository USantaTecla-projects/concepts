import { Injectable } from '@angular/core';
import { toQuestionTypeMappers } from 'src/app/exam/utils/mappers.util';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { QuestionDTO } from 'src/app/shared/types/question/dto/question.dto';
import { QuestionAnswerType } from 'src/app/shared/types/question/enum/question-answer-type.enum';
import { Question } from 'src/app/shared/types/question/model/question.model';

@Injectable({
  providedIn: 'root',
})
export class CorrectionExamMapperService {
  constructor() {}

  mapDTOToExam(examData: ExamData): Exam {
    const { answerList } = examData;

    const questionList: Question[] = answerList.map(answer => {
      const { type } = answer;
      const questionDTO: QuestionDTO = {
        ...answer[`questionT${QuestionAnswerType[type]}`],
      };
      questionDTO[`answerT${QuestionAnswerType[type]}`] = answer;
      return toQuestionTypeMappers[type](questionDTO);
    });

    console.log(questionList);

    return { ...examData, questionList };
  }
}
