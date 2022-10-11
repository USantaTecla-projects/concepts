import { QuestionAnswerType } from '../../types/enums/question-answer-type.enum';
import { QuestionType2 } from '../../types/model/question/question-type/question-type2.model';
import { Question } from '../../types/model/question/question.model';

export const replyQuestionType2 = (userID: number, question: Question, reply: any): QuestionType2 => {
  const { type } = question;

  if (type === QuestionAnswerType.TYPE2 && typeof reply === 'boolean') {
    const questionT2 = <QuestionType2>question;
    return { ...questionT2, answer: { userID, type, reply } };
  }

  throw new Error('Wrong question passed to TYPE 2 replier. This questions is not TYPE 2');
};
