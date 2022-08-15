import { Component, OnInit } from '@angular/core';
import { first, mergeMap, Observable, of, tap } from 'rxjs';
import { Answer } from 'src/app/core/models/answer.model';
import { Concept } from 'src/app/core/models/concept.model';
import { Justification } from 'src/app/core/models/justification.model';
import { KnowledgeService } from 'src/app/core/services/knowledge.service';

@Component({
  selector: 'app-concepts',
  templateUrl: './concepts.page.html',
  styleUrls: ['./concepts.page.scss'],
})
export class ConceptsComponent implements OnInit {
  concepts$!: Observable<Concept[]>;

  answers$!: Observable<Answer[]>;

  justifications$!: Observable<Justification[]>;

  answerId!: number;

  conceptId!: number;

  constructor(private knowledgeService: KnowledgeService) {}

  ngOnInit(): void {
    this.concepts$ = this.knowledgeService.getAllConcepts();
  }

  onConceptSelect(conceptId$: Observable<number>) {
    conceptId$
      .pipe(
        first(),
        tap(id => (this.conceptId = id)),
        mergeMap(id => (this.answers$ = this.knowledgeService.getAllAnswers(id))),
        mergeMap(() => (this.justifications$ = of([])))
      )
      .subscribe();
  }

  onAnswerSelect(answerId$: Observable<number>) {
    answerId$
      .pipe(
        first(),
        tap(id => (this.answerId = id)),
        mergeMap(() => {
          return (this.justifications$ = this.knowledgeService.getAllJustifications(this.conceptId, this.answerId));
        })
      )
      .subscribe();
  }
}
