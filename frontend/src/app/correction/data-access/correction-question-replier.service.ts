import { BehaviorSubject, Observable } from 'rxjs';
import { Question } from 'src/app/exam/types/model/question/question.model';

export class CorrectionQuestionReplierService {
  private correctedQuestionsSubject = new BehaviorSubject<Question[]>([]);

  correctedQuestions$: Observable<Question[]> = this.correctedQuestionsSubject.asObservable();

  constructor() {}

  addCorrectedQuestion(question: Question) {
    const oldCorredtedQuestions = this.correctedQuestionsSubject.getValue();
    const index = oldCorredtedQuestions.findIndex(({ id }) => id === question.id);

    if (index === -1) {
      return this.correctedQuestionsSubject.next([...oldCorredtedQuestions, question]);
    }

    oldCorredtedQuestions[index] = question;
    this.correctedQuestionsSubject.next(oldCorredtedQuestions);
  }

  getCorrectedQuestions() {
    return this.correctedQuestionsSubject.getValue();
  }
}
