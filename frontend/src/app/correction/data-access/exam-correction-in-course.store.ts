import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, tap, throwError } from 'rxjs';
import { ExamMapperService } from 'src/app/exam/data-access/exam-mapper.service';
import { ExamData } from 'src/app/exam/types/dto/exam/exam.dto';
import { Exam } from 'src/app/exam/types/model/exam.model';

@Injectable({
  providedIn: 'root',
})
export class ExamCorrecionInCourseStore {
  private examInCourseSubject = new BehaviorSubject<Exam>({
    id: 0,
    userID: 0,
    creationDate: '',
    questionList: [],
  });

  examInCourse$: Observable<Exam> = this.examInCourseSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  getExam(userID: number, examID: number) {
    return this.httpClient.get<ExamData>(`${userID}/exam/${examID}`).pipe(
      catchError(error => {
        const message = 'Could not get the user exams';
        console.log(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData)),
      tap(exam => this.examInCourseSubject.next(exam))
    );
  }
}
