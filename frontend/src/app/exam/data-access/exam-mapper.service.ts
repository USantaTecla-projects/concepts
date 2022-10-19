import { Injectable } from '@angular/core';
import { AnswerDTO } from '../types/dto/answer.dto';
import { ExamMetadata } from '../types/dto/exam/exam-medata.dto';
import { ExamData } from '../types/dto/exam/exam.dto';
import { ReplyExamDTO } from '../types/dto/exam/reply-exam.dto';
import { QuestionAndAnswerDTO } from '../types/dto/question-answer.dto';
import { Exam } from '../types/model/exam.model';
import { Question } from '../types/model/question/question.model';
import { fromQuestionTypeMappers, toQuestionTypeMappers } from '../utils/mappers.util';

@Injectable({
  providedIn: 'root',
})
export class ExamMapperService {
  constructor() {}

  mapDTOToExam(examData: ExamData): Exam {
    const { id, userID, creationDate, questionDataList, timeSpent } = examData;

    const mappedList: Question[] = questionDataList.map(questionData => {
      const { type } = questionData;
      return toQuestionTypeMappers[type](questionData);
    });

    return { id, userID, creationDate, questionList: mappedList, timeSpent };
  }

  mapExamToDTO(metadata: ExamMetadata, questions: Question[]): ReplyExamDTO {
    const mappedList: QuestionAndAnswerDTO[] = questions.map(question => {
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
