import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, shareReplay, tap, throwError } from 'rxjs';
import { State } from 'src/app/shared/utils/enums/state.enum';

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

  constructor(private httpClient: HttpClient) {}

  createJustification(conceptID: number, answerID: number, justification: Justification) {
    return this.httpClient
      .post<Justification>(`concepts/${conceptID}/answers/${answerID}/justifications/`, justification)
      .pipe(
        catchError(error => {
          const message = 'Could not create the justification';
          console.log(message, error);
          return throwError(() => error);
        }),
        tap(justification => {
          const justifications = this.justificationsSubject.getValue();
          const newJustifications = [...justifications, justification];
          this.justificationsSubject.next(newJustifications);
        }),
        shareReplay()
      );
  }

  readJustifications(conceptID: number, answerID: number) {
    this.stateSubject.next(State.LOADING);

    return this.httpClient.get<Justification[]>(`concepts/${conceptID}/answers/${answerID}/justifications/`).pipe(
      catchError(error => {
        const message = 'Could not load concepts';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(justifications => {
        this.justificationsSubject.next(justifications);
        justifications.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
      })
    );
  }

  updateJustification(conceptID: number, answerID: number, justificationID: number, changes: Partial<Justification>) {
    const justifications = this.justificationsSubject.getValue();
    const index = justifications.findIndex(justification => justification.id === justificationID);

    const newJustification = {
      ...justifications[index],
      ...changes,
    };

    const newJustifications = [...justifications];
    newJustifications[index] = newJustification;

    this.justificationsSubject.next(newJustifications);

    return this.httpClient
      .put(`concepts/${conceptID}/answers/${answerID}/justifications/${justificationID}`, newJustification)
      .pipe(
        catchError(error => {
          const message = 'Could not update the answer';
          console.log(message, error);
          return throwError(() => error);
        }),
        shareReplay()
      );
  }

  deleteJustification(conceptID: number, answerID: number, justificationID: number) {
    const justifications = this.justificationsSubject.getValue();

    const newJustifications = justifications.filter(justification => justification.id !== justificationID);

    this.justificationsSubject.next(newJustifications);

    return this.httpClient.delete(`concepts/${conceptID}/answers/${answerID}/justifications/${justificationID}`).pipe(
      catchError(error => {
        const message = 'Could not delete the answer';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(() => {
        if (newJustifications.length === 0) this.stateSubject.next(State.EMPTY);
      })
    );
  }

  resetJustificationList() {
    this.justificationsSubject.next([]);
    this.stateSubject.next(State.INIT);
  }
}
