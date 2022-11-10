import { Injectable } from '@angular/core';
import { ExamMetadata } from 'src/app/shared/types/exam/dto/exam-medata.dto';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { ReplyExamDTO } from 'src/app/shared/types/exam/dto/reply-exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { AnswerDTO } from '../../shared/types/answer/dto/answer.dto';
import { ExamQuestionAndAnswerDTO } from '../../shared/types/exam/dto/exam-question-answer.dto';
import { Question } from '../../shared/types/question/model/question.model';

import { fromQuestionTypeMappers, toQuestionTypeMappers } from '../utils/mappers.util';

@Injectable({
  providedIn: 'root',
})
export class ExamMapperService {
  constructor() {}

  mapDTOToExam(examData: ExamData): Exam {
    const { questionDataList } = examData;

    const mappedList: Question[] = questionDataList.map(questionData => {
      const { type } = questionData;
      return toQuestionTypeMappers[type](questionData);
    });

    return { ...examData, questionList: mappedList };
  }

  mapExamToDTO(metadata: ExamMetadata, questions: Question[]): ReplyExamDTO {
    const mappedList: ExamQuestionAndAnswerDTO[] = questions.map(question => {
      const { type } = question;
      const questionDTO = fromQuestionTypeMappers[type](question);
      const answerDTO = <AnswerDTO>question['answer'];

      return {
        questionDTO,
        answerDTO,
      };
    });

    return {
      ...metadata,
      questionAndAnswerDTOList: mappedList,
    };
  }
}
