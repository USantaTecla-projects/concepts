import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, shareReplay, tap, throwError } from 'rxjs';

import { State } from 'src/app/shared/utils/enums/state.enum';
import { Page } from 'src/app/shared/utils/page-response.dto';
import { Concept } from './model/concept.model';

@Injectable({
  providedIn: 'root',
})
export class ConceptStore {
  private conceptsSubject = new BehaviorSubject<Concept[]>([]);

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  concepts$: Observable<Concept[]> = this.conceptsSubject.asObservable();

  state$: Observable<string> = this.stateSubject.asObservable();

  constructor(private httpClient: HttpClient) {
    this.readConcepts();
  }

  createConcept(concept: Concept) {
    return this.httpClient.post<Concept>(`concepts/`, concept).pipe(
      catchError(error => {
        const message = 'Could not create the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(concept => {
        const concepts = this.conceptsSubject.getValue();
        const newConcepts = [...concepts, concept];
        this.conceptsSubject.next(newConcepts);
      }),
      shareReplay()
    );
  }

  updateConcept(conceptID: number, changes: Partial<Concept>) {
    const concepts = this.conceptsSubject.getValue();
    const index = concepts.findIndex(concept => concept.id === conceptID);

    const newConcept = {
      ...concepts[index],
      ...changes,
    };

    const newConcepts = [...concepts];
    newConcepts[index] = newConcept;

    this.conceptsSubject.next(newConcepts);

    return this.httpClient.put(`concepts/${conceptID}`, newConcept).pipe(
      catchError((error: HttpErrorResponse) => {
        const message = 'Could not update the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      shareReplay()
    );
  }

  deleteConcept(conceptID: number) {
    const concepts = this.conceptsSubject.getValue();

    const newConcepts = concepts.filter(concept => concept.id !== conceptID);

    this.conceptsSubject.next(newConcepts);

    return this.httpClient.delete(`concepts/${conceptID}`).pipe(
      catchError(error => {
        const message = 'Could not delete the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(() => {
        if (newConcepts.length === 0) this.stateSubject.next(State.EMPTY);
      })
    );
  }

  private readConcepts() {
    this.stateSubject.next(State.LOADING);

    return this.httpClient
      .get<Page<Concept>>('concepts/', { params: new HttpParams().set('page', 0) })
      .pipe(
        map(res => res.content),
        catchError(error => {
          const message = 'Could not load concepts';
          console.log(message, error);
          return throwError(() => error);
        }),
        tap(concepts => {
          this.conceptsSubject.next(concepts);
          concepts.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
        })
      )
      .subscribe();
  }
}
