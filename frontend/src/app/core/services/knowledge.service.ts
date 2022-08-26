import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, shareReplay, tap } from 'rxjs';
import { AnswerDTO } from '../dtos/knowledge/answer.dto';
import { ConceptDTO } from '../dtos/knowledge/concept.dto';
import { JustificationDTO } from '../dtos/knowledge/justification.dto';
import { Page } from '../dtos/page-response.dto';
import { Answer } from '../models/answer.model';
import { Concept } from '../models/concept.model';
import { Justification } from '../models/justification.model';

@Injectable({
  providedIn: 'root',
})
export class KnowledgeService {
  constructor(private httpClient: HttpClient) {}

  findAllConcepts() {
    return this.httpClient.get<Page<Concept>>('concepts/', { params: new HttpParams().set('page', 0) }).pipe(
      shareReplay(),
      map(res => res.content)
    );
  }

  findAllAnswers(conceptId: number) {
    return this.httpClient.get<Answer[]>(`concepts/${conceptId}/answers/`).pipe(shareReplay());
  }

  findAllJustifications(conceptId: number, answerId: number) {
    return this.httpClient
      .get<Justification[]>(`concepts/${conceptId}/answers/${answerId}/justifications/`)
      .pipe(shareReplay());
  }

  updateConcept(conceptId: number, conceptDTO: ConceptDTO) {
    return this.httpClient.put<Concept>(`concepts/${conceptId}`, conceptDTO).pipe(shareReplay());
  }

  updateAnswer(conceptId: number, answerId: number, answerDTO: AnswerDTO) {
    return this.httpClient.put<Answer>(`concepts/${conceptId}/answers/${answerId}`, answerDTO).pipe(shareReplay());
  }

  updateJustification(
    conceptId: number,
    answerId: number,
    justificationId: number,
    justificationDTO: JustificationDTO
  ) {
    console.log(justificationDTO);
    return this.httpClient
      .put<Justification>(`concepts/${conceptId}/answers/${answerId}/justifications/${justificationId}`, {
        ...justificationDTO,
        error: 'asdsa',
      })
      .pipe(shareReplay());
  }
}
