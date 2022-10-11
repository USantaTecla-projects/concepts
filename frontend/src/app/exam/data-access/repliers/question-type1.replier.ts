import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType1 } from '../../types/model/question/question-type/question-type1.model';
import { Question } from '../../types/model/question/question.model';

export const replyQuestionType1 = (userID: number, question: Question, reply: any): QuestionType1 => {
  const { type } = question;

  if (type === QuestionAnswerType.TYPE1 && typeof reply === 'string') {
    const questionT1 = <QuestionType1>question;
    return { ...questionT1, answer: { userID, type, reply } };
  }

  throw new Error('Wrong question passed to TYPE 1 replier. This questions is not TYPE 1');
};
