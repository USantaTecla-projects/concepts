import { Component, ContentChild, EventEmitter, Input, OnInit, Output, TemplateRef } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AnswerDTO } from 'src/app/core/dtos/knowledge/answer.dto';
import { ConceptDTO } from 'src/app/core/dtos/knowledge/concept.dto';
import { JustificationDTO } from 'src/app/core/dtos/knowledge/justification.dto';
import { StateType } from 'src/app/core/enums/state.enum';

@Component({
  selector: 'app-accordion-list-card',
  templateUrl: './accordion-list-card.component.html',
  styleUrls: ['./accordion-list-card.component.scss'],
})
export class AccordionListCardComponent implements OnInit {
  @Input() title!: string;

  @Input() items: any[] | null = [];

  @Input() state: string | null = StateType.INIT;

  @Input() editForm!: FormData;

  @Output() itemSelect: EventEmitter<Observable<number>> = new EventEmitter<Observable<number>>();

  @Output() itemEdit: EventEmitter<any> = new EventEmitter<any>();

  @ContentChild('formEdit') formEdit: TemplateRef<any> | undefined;

  selectedItemId$!: Observable<number>;

  constructor() {}

  ngOnInit(): void {}

  selectItem(itemId: number) {
    this.selectedItemId$ = of(itemId);
    this.itemSelect.emit(this.selectedItemId$);
  }

  editItem(dto: ConceptDTO | AnswerDTO | JustificationDTO) {
    this.itemEdit.emit(dto);
  }
}
