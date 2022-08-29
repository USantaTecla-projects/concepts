import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { State } from 'src/app/shared/utils/state.enum';
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

  setConceptId(conceptID: number) {
    this.conceptID = conceptID;
    this.loadAnswers();
  }

  saveAnswer(answerId: number, cahnges: Partial<Answer>) {}

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
