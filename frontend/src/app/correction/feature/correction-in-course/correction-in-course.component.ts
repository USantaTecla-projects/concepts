import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamQuestionType0Component } from 'src/app/exam/ui/exam-question/exam-question-types/exam-question-type0/exam-question-type0.component';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';
import { User } from 'src/app/shared/types/auth/model/user.model';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { ExamResponse } from 'src/app/shared/types/misc/model/exam-response.model';
import { QuestionAnswerType } from 'src/app/shared/types/question/enum/question-answer-type.enum';
import { CorrecionInCourseStore } from '../../data-access/correction-in-course.store';
import { CorrectionQuestionReplierService } from '../../data-access/correction-question-replier.service';
import { CorrectionQuestionType0Component } from '../../ui/correction-question/correction-question-types/correction-question-type0/correction-question-type0.component';

@Component({
  selector: 'app-correction-in-course',
  templateUrl: './correction-in-course.component.html',
  styleUrls: ['./correction-in-course.component.scss'],
  providers: [CorrectionQuestionReplierService],
})
export class CorrectionInCourseComponent implements OnInit {
  exam$!: Observable<Exam>;

  user$!: Observable<User | null>;

  examResponses$!: Observable<ExamResponse[] | undefined>;

  constructor(
    private router: Router,
    private correctionQuestionReplierService: CorrectionQuestionReplierService,
    private correcionInCourseStore: CorrecionInCourseStore,
    private authStore: AuthStore,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.exam$ = this.correcionInCourseStore.correctionInCourse$;
    this.examResponses$ = this.exam$.pipe(
      map(({ questionList, answerList }) => {
        return answerList.map(answer => {
          const { questionID } = answer;
          const question = questionList.find(({ id }) => id === questionID);
          if (question) {
            return { question, answer };
          }

          return {
            question: {
              id: 0,
              type: QuestionAnswerType.TYPE0,
              examComponentType: ExamQuestionType0Component,
              correctionComponentType: CorrectionQuestionType0Component,
            },
            answer,
          };
        });
      })
    );

    this.user$ = this.authStore.user$;
    const numberOfQuestions = this.correcionInCourseStore.getNumberOfQuestions();
    if (!numberOfQuestions) this.router.navigateByUrl('/correction');
    this.correctionQuestionReplierService.setNumberOfQuestions(numberOfQuestions);
  }

  saveCorrection() {
    const correctedExamResponses = this.correctionQuestionReplierService.getCorrectedQuestions();
    if (correctedExamResponses.length === this.correcionInCourseStore.getNumberOfQuestions()) {
      return this.correcionInCourseStore.updateExam(correctedExamResponses).subscribe({
        next: () => {
          this.snackbarService.openSnackBar('Exam corrected!');
          this.router.navigateByUrl('/correction');
        },
        error: (error: Error) => this.snackbarService.openSnackBar(error.message),
      });
    }

    return this.snackbarService.openSnackBar('All questions should be corrected to finish');
  }
}
