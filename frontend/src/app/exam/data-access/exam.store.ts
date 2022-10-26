import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, concatMap, map, Observable, shareReplay, switchMap, tap, throwError } from 'rxjs';
import { User } from 'src/app/auth/types/model/user.model';
import { Page } from 'src/app/shared/interfaces/page-response.dto';
import { GenerateExamData } from '../types/dto/exam/create-exam.dto';
import { ExamData } from '../types/dto/exam/exam.dto';
import { Exam } from '../types/model/exam.model';
import { Question } from '../types/model/question/question.model';
import { ExamMapperService } from './exam-mapper.service';

@Injectable({
  providedIn: 'root',
})
export class ExamStore {
  private repliedExamsPageSubject = new BehaviorSubject<Page<Exam>>({
    content: [],
    totalPages: 0,
    totalElements: 0,
    numberOfElements: 0,
  });

  private examInCourseSubject = new BehaviorSubject<Exam>({ id: 0, userID: 0, creationDate: '', questionList: [] });

  repliedExamsPage$: Observable<Page<Exam>> = this.repliedExamsPageSubject.asObservable();

  examInCourse$: Observable<Exam> = this.examInCourseSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  // TODO Refactor method to accept the user observale to SwitchMap
  createExam(generateExamData: GenerateExamData): Observable<Exam> {
    return this.httpClient.post<ExamData>('exams/', generateExamData).pipe(
      catchError(error => {
        const message = 'Could not generate the exam';
        console.log(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData)),
      tap(exam => {
        this.examInCourseSubject.next(exam);
      }),
      shareReplay()
    );
  }

  replyExam(repliedQuestions: Question[]) {
    const { id: examID, userID, creationDate } = this.examInCourseSubject.getValue();

    const replyExamDTO = this.examMapperService.mapExamToDTO({ examID, userID, creationDate }, repliedQuestions);

    return this.httpClient.patch<Exam>('exams/', replyExamDTO).pipe(
      catchError(error => {
        const message = 'Could not reply the exam';
        console.log(message, error);
        return throwError(() => new Error(message));
      })
    );
  }

  getUserExams(user$: Observable<User | null>, nextPage: number = 0): Observable<Page<Exam>> {
    return user$.pipe(
      map(user => (user ? user.id : 0)),
      switchMap(userID => this.getUsersExamsObservable(userID, nextPage))
    );
  }

  private getUsersExamsObservable(userID: number, nextPage: number): Observable<Page<Exam>> {
    return this.httpClient
      .get<Page<ExamData>>(`exams/${userID}`, { params: new HttpParams().set('page', nextPage) })
      .pipe(
        catchError(error => {
          const message = 'Could not get the user exams';
          console.log(message, error);
          return throwError(() => new Error(message));
        }),
        map(examDataPage => {
          const { content } = examDataPage;
          const examList = content.map(examData => this.examMapperService.mapDTOToExam(examData));
          return { ...examDataPage, content: examList };
        }),
        tap(examPage => {
          if (examPage.content.length) {
            this.repliedExamsPageSubject.next(examPage);
          }
        }),
        shareReplay()
      );
  }

  getNumberOfQuestions(): number {
    return this.examInCourseSubject.getValue().questionList.length;
  }
}
