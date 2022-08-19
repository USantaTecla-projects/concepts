import { Component, OnInit } from '@angular/core';
import { delay, EMPTY, first, mergeMap, Observable, of, tap } from 'rxjs';
import { AnswerDTO } from 'src/app/core/dtos/knowledge/answer.dto';
import { ConceptDTO } from 'src/app/core/dtos/knowledge/concept.dto';
import { JustificationDTO } from 'src/app/core/dtos/knowledge/justification.dto';
import { StateType } from 'src/app/core/enums/state.enum';
import { Answer } from 'src/app/core/models/answer.model';
import { Concept } from 'src/app/core/models/concept.model';
import { Justification } from 'src/app/core/models/justification.model';
import { KnowledgeService } from 'src/app/core/services/knowledge.service';
import { FormData } from '../../components/form/interfaces/form-data.interface';
import { KNOWLEDGE_FORM_DATA } from '../../data/form/knowledge.data';

@Component({
  selector: 'app-concepts',
  templateUrl: './concepts.page.html',
  styleUrls: ['./concepts.page.scss'],
})
export class ConceptsComponent implements OnInit {
  concepts$!: Observable<Concept[]>;

  answers$!: Observable<Answer[]>;

  justifications$!: Observable<Justification[]>;

  answersState$: Observable<string> = of('INIT');

  justificationsState$: Observable<string> = of('INIT');

  conceptId!: number;

  answerId!: number;

  justificationId!: number;

  conceptForm: FormData = KNOWLEDGE_FORM_DATA['concepts'];

  answerForm: FormData = KNOWLEDGE_FORM_DATA['answers'];

  justificationForm: FormData = KNOWLEDGE_FORM_DATA['justifications'];

  constructor(private knowledgeService: KnowledgeService) {}

  ngOnInit(): void {
    this.concepts$ = this.knowledgeService.findAllConcepts();
    this.answersState$ = of(StateType.EMPTY);
    this.justificationsState$ = of(StateType.EMPTY);
  }

  onConceptSelect(conceptId$: Observable<number>) {
    conceptId$
      .pipe(
        first(),
        mergeMap(id => {
          this.answersState$ = of(StateType.LOADING);
          this.conceptId = id;
          this.justifications$ = of([]);
          return (this.answers$ = this.knowledgeService.findAllAnswers(id));
        }),
        delay(300),
        tap(answers => (this.answersState$ = !answers.length ? of(StateType.EMPTY) : of(StateType.NORMAL)))
      )
      .subscribe();
  }

  onAnswerSelect(answerId$: Observable<number>) {
    answerId$
      .pipe(
        first(),
        mergeMap(id => {
          this.justificationsState$ = of(StateType.LOADING);
          this.answerId = id;
          return (this.justifications$ = this.knowledgeService.findAllJustifications(this.conceptId, this.answerId));
        }),
        delay(300),
        tap(justifications => {
          this.justificationsState$ = !justifications.length ? of(StateType.EMPTY) : of(StateType.NORMAL);
        })
      )
      .subscribe();
  }

  onJustificationSelect(justificationId$: Observable<number>) {
    justificationId$
      .pipe(
        first(),
        tap(id => (this.justificationId = id))
      )
      .subscribe();
  }

  onConceptEdit(conceptDTO: ConceptDTO) {
    this.knowledgeService
      .updateConcept(this.conceptId, conceptDTO)
      .pipe(
        first(),
        mergeMap(() => (this.concepts$ = this.knowledgeService.findAllConcepts()))
      )
      .subscribe();
  }

  onAnswerEdit(answerDTO: AnswerDTO) {
    this.knowledgeService
      .updateAnswer(this.conceptId, this.answerId, answerDTO)
      .pipe(
        first(),
        mergeMap(() => (this.answers$ = this.knowledgeService.findAllAnswers(this.conceptId)))
      )
      .subscribe();
  }

  onJustificationEdit(justificationDTO: JustificationDTO) {
    this.knowledgeService
      .updateJustification(this.conceptId, this.answerId, this.justificationId, justificationDTO)
      .pipe(
        first(),
        mergeMap(
          () => (this.justifications$ = this.knowledgeService.findAllJustifications(this.conceptId, this.answerId))
        )
      )
      .subscribe();
  }
}
