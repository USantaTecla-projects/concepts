import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, tap, throwError } from 'rxjs';
import { ExamMapperService } from 'src/app/exam/data-access/exam-mapper.service';
import { ExamData } from 'src/app/exam/types/dto/exam/exam.dto';
import { Exam } from 'src/app/exam/types/model/exam.model';
import { Question } from 'src/app/exam/types/model/question/question.model';

@Injectable({
  providedIn: 'root',
})
export class CorrecionInCourseStore {
  private correctionInCourseSubject = new BehaviorSubject<Exam>({
    id: 0,
    userID: 0,
    creationDate: '',
    questionList: [],
  });

  correctionInCourse$: Observable<Exam> = this.correctionInCourseSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  getExam(userID: number, examID: number) {
    return this.httpClient.get<ExamData>(`${userID}/exam/${examID}`).pipe(
      catchError(error => {
        const message = 'Could not get the user exams';
        console.error(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData)),
      tap(exam => this.correctionInCourseSubject.next(exam))
    );
  }

  updateExam(correctedQuestions: Question[]) {
    const { id: examID, userID, creationDate } = this.correctionInCourseSubject.getValue();

    const updatedExamDTO = this.examMapperService.mapExamToDTO({ examID, userID, creationDate }, correctedQuestions);

    return this.httpClient.patch<Exam>(`${userID}/exam`, updatedExamDTO).pipe(
      catchError(error => {
        const message = 'Could not reply the exam';
        console.error(message, error);
        return throwError(() => new Error(message));
      })
    );
  }
}
