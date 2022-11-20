import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, switchMap, throwError } from 'rxjs';
import { User } from 'src/app/shared/types/auth/model/user.model';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { Page } from 'src/app/shared/types/misc/dto/page-response.dto';

import { ExamMapperService } from './exam-mapper.service';

@Injectable({
  providedIn: 'root',
})
export class ExamService {
  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  getExam(user$: Observable<User | null>, examID: number) {
    return user$.pipe(
      map(user => (user ? user.id : 0)),
      switchMap(userID => this.getExamObservable(userID, examID))
    );
  }

  private getExamObservable(userID: number, examID: number) {
    return this.httpClient.get<ExamData>(`${userID}/exams/${examID}`).pipe(
      catchError(error => {
        const message = 'Could not get the user exams';
        console.error(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData))
    );
  }

  getUserExams(user$: Observable<User | null>, nextPage = 0, isCorrected: boolean): Observable<Page<Exam>> {
    return user$.pipe(
      map(user => (user ? user.id : 0)),
      switchMap(userID => this.getUsersExamsObservable(userID, nextPage, isCorrected))
    );
  }

  private getUsersExamsObservable(userID: number, nextPage: number, isCorrected: boolean): Observable<Page<Exam>> {
    return this.httpClient
      .get<Page<ExamData>>(`${userID}/exams`, {
        params: {
          page: nextPage,
          isCorrected,
        },
      })
      .pipe(
        catchError(error => {
          const message = 'Could not get the user exams';
          console.error(message, error);
          return throwError(() => new Error(message));
        }),
        map(examDataPage => {
          const { content } = examDataPage;
          const examList = content.map(examData => this.examMapperService.mapDTOToExam(examData));
          return { ...examDataPage, content: examList };
        })
      );
  }
}
