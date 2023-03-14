import { BehaviorSubject, Observable } from 'rxjs';
import { ExamResponse } from 'src/app/shared/types/misc/model/exam-response.model';

export class CorrectionQuestionReplierService {
  private correctedQuestionsSubject = new BehaviorSubject<ExamResponse[]>([]);

  private numberOfQuestions = 0;

  correctedQuestions$: Observable<ExamResponse[]> = this.correctedQuestionsSubject.asObservable();

  constructor() {}

  addCorrectedQuestion(examResponse: ExamResponse) {
    const oldExamResponses = this.correctedQuestionsSubject.getValue();
    const index = oldExamResponses.findIndex(({ question: { id } }) => id === examResponse.question.id);

    if (index === -1) {
      return this.correctedQuestionsSubject.next([...oldExamResponses, examResponse]);
    }

    oldExamResponses[index] = examResponse;
    this.correctedQuestionsSubject.next(oldExamResponses);
  }

  getCorrectedQuestions() {
    return this.correctedQuestionsSubject.getValue();
  }

  setNumberOfQuestions(numberOfQuestions: number) {
    this.numberOfQuestions = numberOfQuestions;
  }
}
