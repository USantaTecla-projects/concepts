import { BehaviorSubject, Observable } from 'rxjs';
import { Question } from '../../shared/types/question/model/question.model';

export class ExamQuestionReplierService {
  private replyQuestionsSubject = new BehaviorSubject<boolean>(false);

  replyQuestions$: Observable<boolean> = this.replyQuestionsSubject.asObservable();

  private examFullyRepliedSubject = new BehaviorSubject<boolean>(false);

  examFullyReplied$: Observable<boolean> = this.examFullyRepliedSubject.asObservable();

  private numberOfQuestions = 0;

  repliedQuestions: Question[] = [];

  constructor() {}

  addRepliedQuestion(question: Question) {
    this.repliedQuestions = [...this.repliedQuestions, question];

    if (this.repliedQuestions.length === this.numberOfQuestions) {
      this.examFullyRepliedSubject.next(true);
    }
  }

  notifyComponentsToSendReplies() {
    this.replyQuestionsSubject.next(true);
  }

  setNumberOfQuestions(numberOfQuestions: number) {
    this.numberOfQuestions = numberOfQuestions;
  }
}
