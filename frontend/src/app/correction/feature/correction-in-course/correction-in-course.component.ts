import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { User } from 'src/app/auth/types/model/user.model';
import { Exam } from 'src/app/exam/types/model/exam.model';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { CorrecionInCourseStore } from '../../data-access/correction-in-course.store';
import { CorrectionQuestionReplierService } from '../../data-access/correction-question-replier.service';

@Component({
  selector: 'app-correction-in-course',
  templateUrl: './correction-in-course.component.html',
  styleUrls: ['./correction-in-course.component.scss'],
  providers: [CorrectionQuestionReplierService],
})
export class CorrectionInCourseComponent implements OnInit {
  exam$!: Observable<Exam>;

  user$!: Observable<User | null>;

  constructor(
    private router: Router,
    private correctionQuestionReplierService: CorrectionQuestionReplierService,
    private correcionInCourseStore: CorrecionInCourseStore,
    private authStore: AuthStore,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.exam$ = this.correcionInCourseStore.correctionInCourse$;
    this.user$ = this.authStore.user$;
  }

  saveCorrection() {
    const correctedQuestions = this.correctionQuestionReplierService.getCorrectedQuestions();
    this.correcionInCourseStore.updateExam(correctedQuestions).subscribe({
      next: () => this.router.navigateByUrl(''),
      error: (error: Error) => this.snackbarService.openSnackBar(error.message),
    });
  }
}
