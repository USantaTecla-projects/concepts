import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-correction-pending-list',
  templateUrl: './correction-pending-list.component.html',
  styleUrls: ['./correction-pending-list.component.scss'],
})
export class CorrectionPendingListComponent implements OnInit {
  list = [
    'Exam 1',
    'Exam 2',
    'Exam 3',
    'Exam 1',
    'Exam 2',
    'Exam 3',
    'Exam 1',
    'Exam 2',
    'Exam 3',
    'Exam 1',
    'Exam 2',
    'Exam 3',
  ];
  constructor() {}

  ngOnInit(): void {}
}
