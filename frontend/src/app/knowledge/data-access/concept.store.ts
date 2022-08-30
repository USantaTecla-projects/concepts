import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, shareReplay, Observable, catchError, throwError, tap } from 'rxjs';

import { Page } from 'src/app/shared/utils/page-response.dto';
import { Answer } from './answer.store';

export interface Concept {
  id: number;
  text: string;
  answers?: Answer[];
}

@Injectable({
  providedIn: 'root',
})
export class ConceptStore {
  private conceptsSubject = new BehaviorSubject<Concept[]>([]);

  concepts$: Observable<Concept[]> = this.conceptsSubject.asObservable();

  constructor(private httpClient: HttpClient) {
    this.loadConcepts();
  }

  saveConcept(conceptID: number, changes: Partial<Concept>) {
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
      catchError(error => {
        const message = 'Could not update the concept';
        console.log(message, error);
        return throwError(() => error);
      }),
      shareReplay()
    );
  }

  private loadConcepts() {
    return this.httpClient
      .get<Page<Concept>>('concepts/', { params: new HttpParams().set('page', 0) })
      .pipe(
        map(res => res.content),
        catchError(error => {
          const message = 'Could not load concepts';
          console.log(message, error);
          return throwError(() => error);
        }),
        tap(concepts => this.conceptsSubject.next(concepts))
      )
      .subscribe();
  }
}
