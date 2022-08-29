import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, throwError, tap } from 'rxjs';
import { State } from 'src/app/shared/utils/state.enum';
import { Answer, AnswerStore } from './answer.store';

export interface Justification {
  id: number;
  text: string;
  correct: boolean;
  error: string;
}

@Injectable({
  providedIn: 'root',
})
export class JustificationStore {
  private justificationsSubject = new BehaviorSubject<Justification[]>([]);

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  justifications$: Observable<Justification[]> = this.justificationsSubject.asObservable();

  state$: Observable<string> = this.stateSubject.asObservable();

  conceptID!: number;

  answerID!: number;

  constructor(private httpClient: HttpClient, private answerStore: AnswerStore) {}

  setAnswerId(answerID: number) {
    this.conceptID = this.answerStore.conceptID;
    this.answerID = answerID;
    this.loadJustifications();
  }

  removeAnswerId() {
    this.justificationsSubject.next([]);
    this.stateSubject.next(State.INIT);
  }

  saveJustification(answerId: number, cahnges: Partial<Answer>) {}

  private loadJustifications() {
    this.stateSubject.next(State.LOADING);

    return this.httpClient
      .get<Justification[]>(`concepts/${this.conceptID}/answers/${this.answerID}/justifications/`)
      .pipe(
        catchError(error => {
          const message = 'Could not load concepts';
          console.log(message, error);
          return throwError(() => error);
        }),
        tap(justifications => {
          this.justificationsSubject.next(justifications);
          justifications.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
        })
      )
      .subscribe();
  }
}
