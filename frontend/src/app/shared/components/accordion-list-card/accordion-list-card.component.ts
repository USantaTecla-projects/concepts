import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Answer } from 'src/app/core/models/answer.model';
import { Concept } from 'src/app/core/models/concept.model';

@Component({
  selector: 'app-accordion-list-card',
  templateUrl: './accordion-list-card.component.html',
  styleUrls: ['./accordion-list-card.component.scss'],
})
export class AccordionListCardComponent implements OnInit {
  @Input() title!: string;

  @Input() items$!: Observable<Concept[]>;

  @Input() state$: Observable<string> = of('INIT');

  @Output() itemSelect: EventEmitter<Observable<number>> = new EventEmitter<Observable<number>>();

  selectedItemId!: Observable<number>;

  constructor() {}

  ngOnInit(): void {}

  selectItem(itemId: number) {
    this.selectedItemId = of(itemId);
    this.itemSelect.emit(this.selectedItemId);
  }
}
