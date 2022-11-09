import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, mergeMap } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamService } from 'src/app/exam/data-access/exam.service';
import { ExamItemDatasource } from 'src/app/profile/data-access/exam-item.datasource';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { CorrecionInCourseStore } from '../../data-access/correction-in-course.store';

@Component({
  selector: 'app-correction-pending-list',
  templateUrl: './correction-pending-list.component.html',
  styleUrls: ['./correction-pending-list.component.scss'],
})
export class CorrectionPendingListComponent implements OnInit {
  examItemDatasource!: ExamItemDatasource;

  list = ['Exam 1'];

  constructor(
    private router: Router,
    private examService: ExamService,
    private correctionInCourseStore: CorrecionInCourseStore,
    private authStore: AuthStore,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.examItemDatasource = new ExamItemDatasource(this.examService, this.authStore);
  }

  onExamCorrection(examID: number) {
    this.authStore.user$
      .pipe(
        map(user => (!user ? -1 : user.id)),
        mergeMap(userID => this.correctionInCourseStore.getExam(userID, examID))
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/correction/in-course'),
        error: (err: Error) => this.snackbarService.openSnackBar(err.message),
      });
  }
}
