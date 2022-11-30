import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, mergeMap, Observable, take } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamService } from 'src/app/exam/data-access/exam.service';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { Page } from 'src/app/shared/types/misc/dto/page-response.dto';
import { CorrecionInCourseStore } from '../../data-access/correction-in-course.store';
import { CorredtedExamItemDatasource } from '../../util/corrected-exam-item.datasource';

@Component({
  selector: 'app-correction-pending-list',
  templateUrl: './correction-pending-list.page.html',
  styleUrls: ['./correction-pending-list.page.scss'],
})
export class CorrectionPendingListPage implements OnInit {
  examItemDatasource!: CorredtedExamItemDatasource;

  hasRepliedExams$!: Observable<Page<Exam>>;

  constructor(
    private router: Router,
    private examService: ExamService,
    private correctionInCourseStore: CorrecionInCourseStore,
    private authStore: AuthStore,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.examItemDatasource = new CorredtedExamItemDatasource(this.examService, this.authStore);
    this.hasRepliedExams$ = this.examService.getUserExams(this.authStore.user$, 0, false);
  }

  onExamCorrection(examID: number) {
    this.authStore.user$
      .pipe(
        take(1),
        map(user => (!user ? -1 : user.id)),
        mergeMap(userID => this.correctionInCourseStore.getExam(userID, examID))
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/correction/in-course'),
        error: (err: Error) => this.snackbarService.openSnackBar(err.message),
      });
  }
}
