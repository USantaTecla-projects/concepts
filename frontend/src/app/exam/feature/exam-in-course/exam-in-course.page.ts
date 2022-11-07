import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { User } from 'src/app/auth/types/model/user.model';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { ExamInCourseStore } from '../../data-access/exam-in-course.store';
import { QuestionReplierService } from '../../data-access/question-replier.service';
import { Exam } from '../../types/model/exam.model';
import { Question } from '../../types/model/question/question.model';

@Component({
  selector: 'app-exam-in-course',
  templateUrl: './exam-in-course.page.html',
  styleUrls: ['./exam-in-course.page.scss'],
  providers: [QuestionReplierService],
})
export class ExamInCoursePage implements OnInit {
  exam$!: Observable<Exam>;

  user$!: Observable<User | null>;

  repliedQuestions: Question[] = [];

  constructor(
    private router: Router,
    private examInCourseStore: ExamInCourseStore,
    private authStore: AuthStore,
    private questionReplierService: QuestionReplierService,
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
            const repliedQuestions = this.questionReplierService.repliedQuestions;
            return this.examInCourseStore.replyExam(repliedQuestions);
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
