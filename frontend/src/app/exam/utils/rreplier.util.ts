import { replyQuestionType0 } from '../data-access/repliers/question-type0.replier';
import { replyQuestionType1 } from '../data-access/repliers/question-type1.replier';
import { replyQuestionType2 } from '../data-access/repliers/question-type2.replier';
import { replyQuestionType3 } from '../data-access/repliers/question-type3.replier';
import { QuestionType0 } from '../types/model/question/question-type/question-type0.model';
import { QuestionType1 } from '../types/model/question/question-type/question-type1.model';
import { QuestionType2 } from '../types/model/question/question-type/question-type2.model';
import { QuestionType3 } from '../types/model/question/question-type/question-type3.model';
import { Question } from '../types/model/question/question.model';

export const questionTypeRepliers: { [key: string]: Function } = {
  TYPE0: (userID: number, question: Question, reply: any): QuestionType0 => replyQuestionType0(userID, question, reply),
  TYPE1: (userID: number, question: Question, reply: any): QuestionType1 => replyQuestionType1(userID, question, reply),
  TYPE2: (userID: number, question: Question, reply: any): QuestionType2 => replyQuestionType2(userID, question, reply),
  TYPE3: (userID: number, question: Question, reply: any): QuestionType3 => replyQuestionType3(userID, question, reply),
};
