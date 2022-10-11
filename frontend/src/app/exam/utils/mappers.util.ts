import { mapQuestionType0 } from '../data-access/mappers/question-type0.mapper';
import { mapQuestionType1 } from '../data-access/mappers/question-type1.mapper';
import { mapQuestionType2 } from '../data-access/mappers/question-type2.mapper';
import { mapQuestionType3 } from '../data-access/mappers/question-type3.mapper';
import { QuestionData } from '../types/dto/question.dto';
import { QuestionType0 } from '../types/model/question/question-type/question-type0.model';
import { QuestionType1 } from '../types/model/question/question-type/question-type1.model';
import { QuestionType2 } from '../types/model/question/question-type/question-type2.model';
import { QuestionType3 } from '../types/model/question/question-type/question-type3.model';

export const questionTypeMappers: { [key: string]: Function } = {
  TYPE0: (questionData: QuestionData): QuestionType0 => mapQuestionType0(questionData),
  TYPE1: (questionData: QuestionData): QuestionType1 => mapQuestionType1(questionData),
  TYPE2: (questionData: QuestionData): QuestionType2 => mapQuestionType2(questionData),
  TYPE3: (questionData: QuestionData): QuestionType3 => mapQuestionType3(questionData),
};
