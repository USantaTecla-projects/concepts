import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first, mergeMap, Observable, of, tap } from 'rxjs';
import { AnswerDTO } from 'src/app/core/dtos/knowledge/answer.dto';
import { ConceptDTO } from 'src/app/core/dtos/knowledge/concept.dto';
import { JustificationDTO } from 'src/app/core/dtos/knowledge/justification.dto';
import { StateType } from 'src/app/core/enums/state.enum';
import { Answer } from 'src/app/core/models/answer.model';
import { Concept } from 'src/app/core/models/concept.model';
import { Justification } from 'src/app/core/models/justification.model';
import { KnowledgeService } from 'src/app/core/services/knowledge.service';
import { KnowledgeItem } from './interfaces/knowledge-item.interface';

@Component({
  selector: 'app-concepts',
  templateUrl: './concepts.page.html',
  styleUrls: ['./concepts.page.scss'],
})
export class ConceptsComponent implements OnInit {
  conceptList: KnowledgeItem<Concept> = {
    items$: of([]),
    state$: of('INIT'),
    selectedItem: -1,
  };

  answerList: KnowledgeItem<Answer> = {
    items$: of([]),
    state$: of('INIT'),
    selectedItem: -1,
  };

  justificationList: KnowledgeItem<Justification> = {
    items$: of([]),
    state$: of('INIT'),
    selectedItem: -1,
  };

  editConceptForm: FormGroup = new FormGroup({});
  editAnswerForm: FormGroup = new FormGroup({});
  editJusitificationForm: FormGroup = new FormGroup({});

  constructor(private knowledgeService: KnowledgeService, private formBldr: FormBuilder) {}

  ngOnInit(): void {
    this.setupInitialState();
    this.setupForms();
  }

  setupForms() {
    this.editConceptForm = this.formBldr.group({
      text: [null, [Validators.required]],
    });

    this.editAnswerForm = this.formBldr.group({
      text: [null, [Validators.required]],
      correct: [null],
    });

    this.editJusitificationForm = this.formBldr.group({
      text: [null, [Validators.required]],
      correct: [null],
      justificationError: [null],
    });
  }

  disableError(checked: boolean) {
    const errorControl = this.editJusitificationForm.controls['justificationError'];
    checked ? errorControl.disable() : errorControl.enable();
  }

  setupInitialState() {
    this.conceptList.items$ = this.knowledgeService
      .findAllConcepts()
      .pipe(tap(() => (this.conceptList.state$ = of(StateType.NORMAL))));
    this.answerList.state$ = of(StateType.EMPTY);
    this.justificationList.state$ = of(StateType.EMPTY);
  }

  onConceptSelect(conceptId$: Observable<number>) {
    this.answerList.state$ = of(StateType.LOADING);

    this.answerList.items$ = conceptId$.pipe(
      tap(id => {
        this.conceptList.selectedItem = id;
        this.justificationList.items$ = of([]);
      }),
      mergeMap(id => this.knowledgeService.findAllAnswers(id)),
      tap(answers => (this.answerList.state$ = !answers.length ? of(StateType.EMPTY) : of(StateType.NORMAL))),
      tap(answers => console.log(answers))
    );
  }

  onAnswerSelect(answerId$: Observable<number>) {
    this.justificationList.state$ = of(StateType.LOADING);

    this.justificationList.items$ = answerId$.pipe(
      tap(id => (this.answerList.selectedItem = id)),
      mergeMap(() =>
        this.knowledgeService.findAllJustifications(this.conceptList.selectedItem, this.answerList.selectedItem)
      ),
      tap(justifications => {
        this.justificationList.state$ = !justifications.length ? of(StateType.EMPTY) : of(StateType.NORMAL);
      })
    );
  }

  onJustificationSelect(justificationId$: Observable<number>) {
    justificationId$
      .pipe(
        first(),
        tap(id => (this.justificationList.selectedItem = id))
      )
      .subscribe();
  }

  onConceptEdit(conceptDTO: ConceptDTO) {
    this.conceptList.items$ = this.knowledgeService
      .updateConcept(this.conceptList.selectedItem, conceptDTO)
      .pipe(mergeMap(() => this.knowledgeService.findAllConcepts()));
  }

  onAnswerEdit(answerDTO: AnswerDTO) {
    console.log(answerDTO);
    this.answerList.items$ = this.knowledgeService
      .updateAnswer(this.conceptList.selectedItem, this.answerList.selectedItem, answerDTO)
      .pipe(mergeMap(() => this.knowledgeService.findAllAnswers(this.conceptList.selectedItem)));
  }

  onJustificationEdit(justificationDTO: JustificationDTO) {
    this.justificationList.items$ = this.knowledgeService
      .updateJustification(
        this.conceptList.selectedItem,
        this.answerList.selectedItem,
        this.justificationList.selectedItem,
        justificationDTO
      )
      .pipe(
        mergeMap(() =>
          this.knowledgeService.findAllJustifications(this.conceptList.selectedItem, this.answerList.selectedItem)
        )
      );
  }
}
