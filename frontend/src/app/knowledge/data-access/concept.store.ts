import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, shareReplay, tap, throwError } from 'rxjs';

import { State } from 'src/app/shared/interfaces/enums/state.enum';
import { Page } from 'src/app/shared/interfaces/page-response.dto';
import { Concept } from '../interfaces/concept.model';

@Injectable({
  providedIn: 'root',
})
export class ConceptStore {
  private conceptsPageSubject = new BehaviorSubject<Page<Concept>>({
    content: [],
    totalPages: 0,
    totalElements: 0,
    numberOfElements: 0,
  });

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  conceptsPage$: Observable<Page<Concept>> = this.conceptsPageSubject.asObservable();

  state$: Observable<string> = this.stateSubject.asObservable();

  constructor(private httpClient: HttpClient) {
    this.getConcepts().subscribe();
  }

  createConcept(concept: Concept): Observable<Concept> {
    return this.httpClient.post<Concept>(`concepts/`, concept).pipe(
      catchError(error => {
        const message = 'Could not create the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(concept => {
        const conceptsPage = this.conceptsPageSubject.getValue();
        const { content, totalPages, totalElements, numberOfElements } = { ...conceptsPage };

        let newPage = { ...conceptsPage };

        if (content.length < 10) {
          newPage = {
            ...newPage,
            content: [...content, concept],
            numberOfElements: numberOfElements + 1,
          };
        }

        newPage = {
          ...newPage,
          totalElements: totalElements + 1,
          totalPages: totalPages + 1,
        };

        this.conceptsPageSubject.next(newPage);
      }),
      shareReplay()
    );
  }

  updateConcept(conceptID: number, changes: Partial<Concept>): Observable<Concept> {
    const conceptsPage = this.conceptsPageSubject.getValue();
    const { content } = { ...conceptsPage };

    const index = content.findIndex(concept => concept.id === conceptID);

    const newConcept = {
      ...content[index],
      ...changes,
    };

    const newContent = [...content];
    newContent[index] = newConcept;

    const newPage = { ...conceptsPage, content: newContent };
    this.conceptsPageSubject.next(newPage);

    return this.httpClient.put<Concept>(`concepts/${conceptID}`, newConcept).pipe(
      catchError((error: HttpErrorResponse) => {
        const message = 'Could not update the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      shareReplay()
    );
  }

  deleteConcept(conceptID: number): Observable<Concept> {
    const conceptsPage = this.conceptsPageSubject.getValue();
    const { content } = { ...conceptsPage };

    const newContent = content.filter(concept => concept.id !== conceptID);
    const newPage = { ...conceptsPage, content: newContent };

    this.conceptsPageSubject.next(newPage);

    return this.httpClient.delete<Concept>(`concepts/${conceptID}`).pipe(
      catchError(error => {
        const message = 'Could not delete the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(() => {
        if (newContent.length === 0) this.stateSubject.next(State.EMPTY);
      })
    );
  }

  getConcepts(nextPage: number = 0): Observable<Concept[]> {
    this.stateSubject.next(State.LOADING);

    return this.httpClient.get<Page<Concept>>('concepts/', { params: new HttpParams().set('page', nextPage) }).pipe(
      catchError(error => {
        const message = 'Could not load concepts';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(page => this.conceptsPageSubject.next(page)),
      map(page => page.content),
      tap(concepts => {
        concepts.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
      })
    );
  }
}
