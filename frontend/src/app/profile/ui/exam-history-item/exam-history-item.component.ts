import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-exam-history-item',
  templateUrl: './exam-history-item.component.html',
  styleUrls: ['./exam-history-item.component.scss'],
})
export class ExamHistoryItemComponent implements OnInit {
  @Input() mark!: number;

  constructor() {}

  ngOnInit(): void {}
}
