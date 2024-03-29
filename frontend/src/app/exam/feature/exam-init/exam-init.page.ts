import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { mergeMap } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { AuthStore } from '../../../auth/data-access/auth.store';
import { GenerateExamData } from '../../../shared/types/exam/dto/create-exam.dto';
import { ExamInCourseStore } from '../../data-access/exam-in-course.store';

@Component({
  selector: 'app-exam-init',
  templateUrl: './exam-init.page.html',
  styleUrls: ['./exam-init.page.scss'],
})
export class ExamInitPage {
  constructor(
    private router: Router,
    private examInCourseStore: ExamInCourseStore,
    private snackbarService: SnackbarService,
    private authStore: AuthStore
  ) {}

  onExamStart(generateExamData: GenerateExamData) {
    this.authStore.user$
      .pipe(
        take(1),
        map(user => (!user ? -1 : user.id)),
        mergeMap(userID => this.examInCourseStore.createExam({ ...generateExamData, userID }))
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/exam/in-course'),
        error: (err: Error) => this.snackbarService.openSnackBar(err.message),
      });
  }
}
