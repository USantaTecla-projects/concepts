import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { User } from 'src/app/shared/types/auth/model/user.model';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { Question } from '../../../shared/types/question/model/question.model';
import { ExamInCourseStore } from '../../data-access/exam-in-course.store';
import { ExamQuestionReplierService } from '../../data-access/exam-question-replier.service';

@Component({
  selector: 'app-exam-in-course',
  templateUrl: './exam-in-course.page.html',
  styleUrls: ['./exam-in-course.page.scss'],
  providers: [ExamQuestionReplierService],
})
export class ExamInCoursePage implements OnInit {
  exam$!: Observable<Exam>;

  user$!: Observable<User | null>;

  repliedQuestions: Question[] = [];

  constructor(
    private router: Router,
    private examInCourseStore: ExamInCourseStore,
    private authStore: AuthStore,
    private questionReplierService: ExamQuestionReplierService,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.exam$ = this.examInCourseStore.examInCourse$;
    this.user$ = this.authStore.user$;

    const numberOfQuestions = this.examInCourseStore.getNumberOfQuestions();
    if (!numberOfQuestions) this.router.navigateByUrl('/exam/init');
    this.questionReplierService.setNumberOfQuestions(numberOfQuestions);
  }

  onExamReply() {
    this.questionReplierService.notifyComponentsToSendReplies();
    this.questionReplierService.examFullyReplied$
      .pipe(
        switchMap(replied => {
          if (replied) {
            const examResponses = this.questionReplierService.examResponses;
            return this.examInCourseStore.updateExam(examResponses);
          }

          return of(null);
        })
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/exam/end'),
        error: (error: Error) => this.snackbarService.openSnackBar(error.message),
      });
  }
}
