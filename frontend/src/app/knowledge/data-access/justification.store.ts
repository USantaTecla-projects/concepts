import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, shareReplay, tap, throwError } from 'rxjs';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { Justification } from './model/justification.model';

@Injectable({
  providedIn: 'root',
})
export class JustificationStore {
  private justificationsListSubject = new BehaviorSubject<Justification[]>([]);

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  justificationsList$: Observable<Justification[]> = this.justificationsListSubject.asObservable();

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
          const justifications = this.justificationsListSubject.getValue();
          const newJustifications = [...justifications, justification];
          this.justificationsListSubject.next(newJustifications);
        }),
        shareReplay()
      );
  }

  getJustifications(conceptID: number, answerID: number) {
    this.stateSubject.next(State.LOADING);

    return this.httpClient.get<Justification[]>(`concepts/${conceptID}/answers/${answerID}/justifications/`).pipe(
      catchError(error => {
        const message = 'Could not load concepts';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(justifications => {
        this.justificationsListSubject.next(justifications);
        justifications.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
      })
    );
  }

  updateJustification(conceptID: number, answerID: number, justificationID: number, changes: Partial<Justification>) {
    const justifications = this.justificationsListSubject.getValue();
    const index = justifications.findIndex(justification => justification.id === justificationID);

    const newJustification = {
      ...justifications[index],
      ...changes,
    };

    const newJustifications = [...justifications];
    newJustifications[index] = newJustification;

    this.justificationsListSubject.next(newJustifications);

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
    const justifications = this.justificationsListSubject.getValue();

    const newJustifications = justifications.filter(justification => justification.id !== justificationID);

    this.justificationsListSubject.next(newJustifications);

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
    this.justificationsListSubject.next([]);
    this.stateSubject.next(State.INIT);
  }
}
