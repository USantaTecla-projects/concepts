import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-accordion-list-item',
  templateUrl: './accordion-list-item.component.html',
  styleUrls: ['./accordion-list-item.component.scss'],
})
export class AccordionListItemComponent implements OnInit {
  @Input() name: string = '';

  constructor() {}

  ngOnInit(): void {}
}
