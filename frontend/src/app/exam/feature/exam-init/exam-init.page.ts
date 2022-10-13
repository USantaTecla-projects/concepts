import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { GenerateExamData } from '../../types/dto/exam/create-exam.dto';
import { ExamStore } from '../../data-access/exam.store';
import { AuthStore } from '../../../auth/data-access/auth.store';
import { mergeMap, switchMap, throwError } from 'rxjs';
import { catchError, filter, flatMap, map, merge } from 'rxjs/operators';

@Component({
  selector: 'app-exam-init',
  templateUrl: './exam-init.page.html',
  styleUrls: ['./exam-init.page.scss'],
})
export class ExamInitPage implements OnInit {
  constructor(
    private router: Router,
    private examStore: ExamStore,
    private snackbarService: SnackbarService,
    private authStore: AuthStore
  ) {}

  ngOnInit(): void {}

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
