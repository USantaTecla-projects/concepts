import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, switchMap, throwError } from 'rxjs';
import { User } from 'src/app/auth/types/model/user.model';
import { Page } from 'src/app/shared/interfaces/page-response.dto';
import { ExamData } from '../types/dto/exam/exam.dto';
import { Exam } from '../types/model/exam.model';
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
    return this.httpClient.get<ExamData>(`${userID}/exam/${examID}`).pipe(
      catchError(error => {
        const message = 'Could not get the user exams';
        console.error(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData))
    );
  }

  getUserExams(user$: Observable<User | null>, nextPage = 0): Observable<Page<Exam>> {
    return user$.pipe(
      map(user => (user ? user.id : 0)),
      switchMap(userID => this.getUsersExamsObservable(userID, nextPage))
    );
  }

  private getUsersExamsObservable(userID: number, nextPage: number): Observable<Page<Exam>> {
    return this.httpClient
      .get<Page<ExamData>>(`${userID}/exams`, {
        params: new HttpParams().set('page', nextPage),
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
