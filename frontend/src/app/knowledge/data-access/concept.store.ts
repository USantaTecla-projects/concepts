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
  private subject = new BehaviorSubject<Concept[]>([]);

  concepts$: Observable<Concept[]> = this.subject.asObservable();

  constructor(private httpClient: HttpClient) {
    this.loadConcepts();
  }

  saveConcept(conceptId: number, cahnges: Partial<Concept>) {}

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
        tap(concepts => this.subject.next(concepts))
      )
      .subscribe();
  }
}
