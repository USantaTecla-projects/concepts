import { Component, ContentChild, Input, TemplateRef } from '@angular/core';
import { InfiniteScrollListContex } from './infinite-scroll-list.context';

@Component({
  selector: 'app-infinite-scroll-list',
  templateUrl: './infinite-scroll-list.component.html',
  styleUrls: ['./infinite-scroll-list.component.scss'],
})
export class InfiniteScrollListComponent<T> {
  @Input() elementSize = 80;

  @Input() datasource!: any;

  @ContentChild('elementTemplate') elementTemplate: TemplateRef<InfiniteScrollListContex<T>> | undefined;

  constructor() {}
}
