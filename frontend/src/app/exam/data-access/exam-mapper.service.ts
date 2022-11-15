import { Injectable } from '@angular/core';
import { AnswerDTO } from 'src/app/shared/types/answer/dto/answer.dto';
import { Answer } from 'src/app/shared/types/answer/model/answer.model';
import { ExamMetadata } from 'src/app/shared/types/exam/dto/exam-medata.dto';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { ReplyExamDTO } from 'src/app/shared/types/exam/dto/reply-exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { ExamResponse } from 'src/app/shared/types/misc/model/exam-response.model';
import { QuestionDTO } from 'src/app/shared/types/question/dto/question.dto';
import { ExamQuestionAndAnswerDTO } from '../../shared/types/exam/dto/exam-question-answer.dto';
import { Question } from '../../shared/types/question/model/question.model';

import { fromQuestionTypeMappers, toQuestionTypeMappers } from '../utils/mappers.util';

@Injectable({
  providedIn: 'root',
})
export class ExamMapperService {
  constructor() {}

  mapDTOToExam(examData: ExamData): Exam {
    const { questionList, answerList } = examData;

    const questionMappedList: Question[] = questionList.map(questionData => {
      const { type } = questionData;
      return toQuestionTypeMappers[type](questionData);
    });

    const answerMappedList: Answer[] = answerList ? answerList.map(answer => ({ ...answer })) : [];

    return { ...examData, questionList: questionMappedList, answerList: answerMappedList };
  }

  mapExamToDTO(metadata: ExamMetadata, examResponses: ExamResponse[]): ReplyExamDTO {
    const mappedList: ExamQuestionAndAnswerDTO[] = examResponses.map(({ question, answer }) => {
      const { type } = question;
      const questionDTO: QuestionDTO = fromQuestionTypeMappers[type](question);
      const answerDTO: AnswerDTO = { ...answer };

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
