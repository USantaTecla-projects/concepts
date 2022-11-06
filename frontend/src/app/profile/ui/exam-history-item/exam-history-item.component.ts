import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-exam-history-item',
  templateUrl: './exam-history-item.component.html',
  styleUrls: ['./exam-history-item.component.scss'],
})
export class ExamHistoryItemComponent {
  @Input() title!: string;

  @Input() mark!: string;

  @Input() date!: string;

  @Input() timeSpent!: number | undefined;

  @Input() isOffline: boolean = false;

  constructor() {}
}
