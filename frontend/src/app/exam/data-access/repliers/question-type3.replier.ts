import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType3 } from '../../types/model/question/question-type/question-type3.model';
import { Question } from '../../types/model/question/question.model';

export const replyQuestionType3 = (userID: number, question: Question, reply: any): QuestionType3 => {
  const { type } = question;

  if (type === QuestionAnswerType.TYPE3 && typeof reply === 'boolean') {
    const questionT3 = <QuestionType3>question;
    return { ...questionT3, answer: { userID, type, reply } };
  }

  throw new Error('Wrong question passed to TYPE 3 replier. This questions is not TYPE 3');
};
