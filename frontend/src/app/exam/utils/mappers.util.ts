import { QuestionType0 } from 'src/app/shared/types/question/model/question-type0.model';
import { QuestionType1 } from 'src/app/shared/types/question/model/question-type1.model';
import { QuestionType2 } from 'src/app/shared/types/question/model/question-type2.model';
import { QuestionType3 } from 'src/app/shared/types/question/model/question-type3.model';
import { QuestionDTO } from '../../shared/types/question/dto/question.dto';
import { Question } from '../../shared/types/question/model/question.model';
import { mapFromQuestionType0, mapToQuestionType0 } from '../data-access/mappers/question-type0.mapper';
import { mapFromQuestionType1, mapToQuestionType1 } from '../data-access/mappers/question-type1.mapper';
import { mapFromQuestionType2, mapToQuestionType2 } from '../data-access/mappers/question-type2.mapper';
import { mapFromQuestionType3, mapToQuestionType3 } from '../data-access/mappers/question-type3.mapper';

export const toQuestionTypeMappers: { [key: string]: Function } = {
  TYPE0: (questionData: QuestionDTO): QuestionType0 => mapToQuestionType0(questionData),
  TYPE1: (questionData: QuestionDTO): QuestionType1 => mapToQuestionType1(questionData),
  TYPE2: (questionData: QuestionDTO): QuestionType2 => mapToQuestionType2(questionData),
  TYPE3: (questionData: QuestionDTO): QuestionType3 => mapToQuestionType3(questionData),
};

export const fromQuestionTypeMappers: { [key: number]: Function } = {
  0: (question: Question): QuestionDTO => mapFromQuestionType0(question),
  1: (question: Question): QuestionDTO => mapFromQuestionType1(question),
  2: (question: Question): QuestionDTO => mapFromQuestionType2(question),
  3: (question: Question): QuestionDTO => mapFromQuestionType3(question),
};
