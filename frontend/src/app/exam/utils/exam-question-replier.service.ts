import { BehaviorSubject, Observable } from 'rxjs';
import { ExamResponse } from 'src/app/shared/types/misc/model/exam-response.model';

export class ExamQuestionReplierService {
  private replyQuestionsSubject = new BehaviorSubject<boolean>(false);

  replyQuestions$: Observable<boolean> = this.replyQuestionsSubject.asObservable();

  private examFullyRepliedSubject = new BehaviorSubject<boolean>(false);

  examFullyReplied$: Observable<boolean> = this.examFullyRepliedSubject.asObservable();

  private numberOfQuestions = 0;

  examResponses: ExamResponse[] = [];

  constructor() {}

  addRepliedQuestion(response: ExamResponse) {
    this.examResponses = [...this.examResponses, response];

    if (this.examResponses.length === this.numberOfQuestions) {
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
