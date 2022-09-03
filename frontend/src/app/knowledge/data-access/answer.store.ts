import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, shareReplay, tap, throwError } from 'rxjs';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { Justification } from './justification.store';

export interface Answer {
  id: number;
  text: string;
  correct: boolean;
  justifications?: Justification[];
}

@Injectable({
  providedIn: 'root',
})
export class AnswerStore {
  private answersSubject = new BehaviorSubject<Answer[]>([]);

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  answers$: Observable<Answer[]> = this.answersSubject.asObservable();

  state$: Observable<string> = this.stateSubject.asObservable();

  conceptID!: number;

  constructor(private httpClient: HttpClient) {}

  setConceptID(conceptID: number) {
    this.conceptID = conceptID;
    this.loadAnswers();
  }

  removeConceptID() {
    this.answersSubject.next([]);
    this.stateSubject.next(State.INIT);
  }

  createAnswer(answer: any) {
    return this.httpClient.post<Answer>(`concepts/${this.conceptID}/answers/`, answer).pipe(
      catchError(error => {
        const message = 'Could not create the answer';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(answer => {
        const answers = this.answersSubject.getValue();
        const newAnswers = [...answers, answer];
        this.answersSubject.next(newAnswers);
      }),
      shareReplay()
    );
  }

  saveAnswer(answerID: number, changes: Partial<Answer>) {
    const answers = this.answersSubject.getValue();
    const index = answers.findIndex(answer => answer.id === answerID);

    const newAnswer = {
      ...answers[index],
      ...changes,
    };

    const newAnswers = [...answers];
    newAnswers[index] = newAnswer;

    this.answersSubject.next(newAnswers);

    return this.httpClient.put(`concepts/${this.conceptID}/answers/${answerID}`, newAnswer).pipe(
      catchError(error => {
        const message = 'Could not update the answer';
        console.log(message, error);
        return throwError(() => error);
      }),
      shareReplay()
    );
  }

  deleteAnswer(answerID: number) {
    const answers = this.answersSubject.getValue();

    const newAnswers = answers.filter(answer => answer.id !== answerID);

    this.answersSubject.next(newAnswers);

    return this.httpClient.delete(`concepts/${this.conceptID}/answers/${answerID}`).pipe(
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

  private loadAnswers() {
    this.stateSubject.next(State.INIT);

    return this.httpClient
      .get<Answer[]>(`concepts/${this.conceptID}/answers/`)
      .pipe(
        catchError(error => {
          const message = 'Could not load concepts';
          console.log(message, error);
          return throwError(() => error);
        }),
        tap(answers => {
          this.answersSubject.next(answers);
          answers.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
        })
      )
      .subscribe();
  }
}
