import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { mergeMap } from 'rxjs';
import { map } from 'rxjs/operators';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { AuthStore } from '../../../auth/data-access/auth.store';
import { ExamStore } from '../../data-access/exam.store';
import { GenerateExamData } from '../../types/dto/exam/create-exam.dto';

@Component({
  selector: 'app-exam-init',
  templateUrl: './exam-init.page.html',
  styleUrls: ['./exam-init.page.scss'],
})
export class ExamInitPage {
  constructor(
    private router: Router,
    private examStore: ExamStore,
    private snackbarService: SnackbarService,
    private authStore: AuthStore
  ) {}

  onExamStart(generateExamData: GenerateExamData) {
    this.authStore.user$
      .pipe(
        map(user => (!user ? -1 : user.id)),
        mergeMap(userID => this.examStore.createExam({ ...generateExamData, userID }))
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/exam/in-course'),
        error: (err: Error) => this.snackbarService.openSnackBar(err.message),
      });
  }
}
