import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, shareReplay, tap } from 'rxjs';
import { Page } from '../dtos/page-response.dto';
import { Answer } from '../models/answer.model';
import { Concept } from '../models/concept.model';
import { Justification } from '../models/justification.model';

@Injectable({
  providedIn: 'root',
})
export class KnowledgeService {
  constructor(private httpClient: HttpClient) {}

  getAllConcepts() {
    return this.httpClient
      .get<Page<Concept>>('concepts/', { params: new HttpParams().set('page', 0) })
      .pipe(
        shareReplay(),
        map(res => res.content)
      );
  }

  getAllAnswers(conceptId: number) {
    return this.httpClient.get<Answer[]>(`concepts/${conceptId}/answers/`).pipe(
      shareReplay(),
      tap(res => console.log(res))
    );
  }

  getAllJustifications(conceptId: number, answerId: number) {
    return this.httpClient
      .get<Justification[]>(`concepts/${conceptId}/answers/${answerId}/justifications/`)
      .pipe(
        shareReplay(),
        tap(res => console.log(res))
      );
  }
}
