import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { take, last, tap, Observable, share, switchMap, throwError, of, Subscription } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { ExamStore } from '../../data-access/exam.store';
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

  repliedQuestions: Question[] = [];

  constructor(
    private router: Router,
    public examStore: ExamStore,
    public authStore: AuthStore,
    private questionReplierService: QuestionReplierService,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    const numberOfQuestions = this.examStore.getNumberOfQuestions();
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
            return this.examStore.replyExam(repliedQuestions);
          }

          return of(null);
        })
      )
      .subscribe({
        next: () => this.router.navigateByUrl('/exam/end'),
        error: (err: Error) => this.snackbarService.openSnackBar(err.message),
      });
  }
}
