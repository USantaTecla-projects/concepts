import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType0 } from '../../types/model/question/question-type/question-type0.model';
import { Question } from '../../types/model/question/question.model';

export const replyQuestionType0 = (userID: number, question: Question, reply: any): QuestionType0 => {
  const { type } = question;
  if (type === QuestionAnswerType.TYPE0 && typeof reply === 'string') {
    const questionT0 = <QuestionType0>question;
    return { ...questionT0, answer: { userID, type, reply } };
  }

  throw new Error('Wrong question passed to TYPE 0 replier. This questions is not TYPE 0');
};
