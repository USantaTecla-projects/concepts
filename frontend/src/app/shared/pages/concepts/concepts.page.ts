import { Component, OnInit } from '@angular/core';
import { delay, EMPTY, first, mergeMap, Observable, of, tap } from 'rxjs';
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

  answersState$: Observable<string> = of('INIT');

  justifications$!: Observable<Justification[]>;

  justificationsState$: Observable<string> = of('INIT');

  answerId!: number;

  conceptId!: number;

  constructor(private knowledgeService: KnowledgeService) {}

  ngOnInit(): void {
    this.concepts$ = this.knowledgeService.getAllConcepts();
  }

  onConceptSelect(conceptId$: Observable<number>) {
    conceptId$
      .pipe(
        tap(() => (this.answersState$ = of('LOADING'))),
        first(),
        tap(id => (this.conceptId = id)),
        mergeMap(id => {
          return (this.answers$ = this.knowledgeService.getAllAnswers(id));
        }),
        tap(answers => (this.answersState$ = !answers.length ? of('EMPTY') : of('NORMAL'))),
        mergeMap(() => (this.justifications$ = of([])))
      )
      .subscribe();
  }

  onAnswerSelect(answerId$: Observable<number>) {
    answerId$
      .pipe(
        tap(() => (this.justificationsState$ = of('LOADING'))),
        first(),
        tap(id => (this.answerId = id)),
        delay(300),
        mergeMap(() => {
          return (this.justifications$ = this.knowledgeService.getAllJustifications(this.conceptId, this.answerId));
        }),
        tap(justifications => (this.justificationsState$ = !justifications.length ? of('EMPTY') : of('NORMAL')))
      )
      .subscribe();
  }
}
