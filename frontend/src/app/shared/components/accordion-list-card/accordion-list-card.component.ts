import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first, mergeMap, Observable, of } from 'rxjs';
import { AnswerDTO } from 'src/app/core/dtos/knowledge/answer.dto';
import { ConceptDTO } from 'src/app/core/dtos/knowledge/concept.dto';
import { JustificationDTO } from 'src/app/core/dtos/knowledge/justification.dto';
import { StateType } from 'src/app/core/enums/state.enum';
import { KnowledgeService } from 'src/app/core/services/knowledge.service';
import { KNOWLEDGE_FORM_DATA } from '../../data/form/knowledge.data';
import { FormData } from '../form/interfaces/form-data.interface';

@Component({
  selector: 'app-accordion-list-card',
  templateUrl: './accordion-list-card.component.html',
  styleUrls: ['./accordion-list-card.component.scss'],
})
export class AccordionListCardComponent<T> implements OnInit {
  @Input() title!: string;

  @Input() items$!: Observable<any[]>;

  @Input() state$: Observable<string> = of(StateType.INIT);

  @Input() editForm!: FormData;

  @Output() itemSelect: EventEmitter<Observable<number>> = new EventEmitter<Observable<number>>();

  @Output() itemEdit: EventEmitter<any> = new EventEmitter<any>();

  selectedItemId$!: Observable<number>;

  constructor() {}

  ngOnInit(): void {}

  selectItem(itemId: number) {
    this.selectedItemId$ = of(itemId);
    this.itemSelect.emit(this.selectedItemId$);
  }

  editItem(dto: ConceptDTO | AnswerDTO | JustificationDTO) {
    console.log(dto);
    this.itemEdit.emit(dto);
  }
}
