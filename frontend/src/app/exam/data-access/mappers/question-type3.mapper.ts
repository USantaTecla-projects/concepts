import { QuestionData } from '../../types/dto/question.dto';
import { QuestionType3 } from '../../types/model/question/question-type/question-type3.dto';
import { ExamQuestionType3Component } from '../../ui/question/exam-question-type3/exam-question-type3.component';

export function mapQuestionType3(questionData: QuestionData): QuestionType3 {
  const { conceptText, incorrectAnswerText, justificationText } = questionData;
  return { type: ExamQuestionType3Component, conceptText, incorrectAnswerText, justificationText };
}
