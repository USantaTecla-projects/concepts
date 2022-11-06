import { Component, OnInit } from '@angular/core';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { ExamItemDatasource } from 'src/app/profile/data-access/exam-item.datasource';

@Component({
  selector: 'app-correction-pending-list',
  templateUrl: './correction-pending-list.component.html',
  styleUrls: ['./correction-pending-list.component.scss'],
})
export class CorrectionPendingListComponent implements OnInit {
  examItemDatasource!: ExamItemDatasource;

  list = ['Exam 1'];

  constructor(private examStore: ExamStore, private authStore: AuthStore) {}

  ngOnInit(): void {
    this.examItemDatasource = new ExamItemDatasource(this.examStore, this.authStore);
  }
}
