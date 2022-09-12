import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, shareReplay, tap, throwError } from 'rxjs';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { Answer } from './model/answer.model';

@Injectable({
  providedIn: 'root',
})
export class AnswerStore {
  private answersListSubject = new BehaviorSubject<Answer[]>([]);

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  answersList$: Observable<Answer[]> = this.answersListSubject.asObservable();

  state$: Observable<string> = this.stateSubject.asObservable();

  constructor(private httpClient: HttpClient) {}

  createAnswer(conceptID: number, answer: any) {
    return this.httpClient.post<Answer>(`concepts/${conceptID}/answers/`, answer).pipe(
      catchError(error => {
        const message = 'Could not create the answer';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(answer => {
        const answers = this.answersListSubject.getValue();
        const newAnswers = [...answers, answer];
        this.answersListSubject.next(newAnswers);
      }),
      shareReplay()
    );
  }

  getAnswers(conceptID: number) {
    this.stateSubject.next(State.LOADING);

    return this.httpClient.get<Answer[]>(`concepts/${conceptID}/answers/`).pipe(
      catchError(error => {
        const message = 'Could not load concepts';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(answers => {
        this.answersListSubject.next(answers);
        answers.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
      })
    );
  }

  updateAnswer(conceptID: number, answerID: number, changes: Partial<Answer>) {
    const answers = this.answersListSubject.getValue();
    const index = answers.findIndex(answer => answer.id === answerID);

    const newAnswer = {
      ...answers[index],
      ...changes,
    };

    const newAnswers = [...answers];
    newAnswers[index] = newAnswer;

    this.answersListSubject.next(newAnswers);

    return this.httpClient.put(`concepts/${conceptID}/answers/${answerID}`, newAnswer).pipe(
      catchError(error => {
        const message = 'Could not update the answer';
        console.log(message, error);
        return throwError(() => error);
      }),
      shareReplay()
    );
  }

  deleteAnswer(conceptID: number, answerID: number) {
    const answers = this.answersListSubject.getValue();

    const newAnswers = answers.filter(answer => answer.id !== answerID);

    this.answersListSubject.next(newAnswers);

    return this.httpClient.delete(`concepts/${conceptID}/answers/${answerID}`).pipe(
      catchError(error => {
        const message = 'Could not delete the answer';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(() => {
        if (newAnswers.length === 0) this.stateSubject.next(State.EMPTY);
      })
    );
  }
}
