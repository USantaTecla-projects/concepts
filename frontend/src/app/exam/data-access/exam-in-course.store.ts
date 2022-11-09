import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, shareReplay, tap, throwError } from 'rxjs';
import { GenerateExamData } from '../types/dto/exam/create-exam.dto';
import { ExamData } from '../types/dto/exam/exam.dto';
import { Exam } from '../types/model/exam.model';
import { Question } from '../types/model/question/question.model';
import { ExamMapperService } from './exam-mapper.service';

@Injectable({
  providedIn: 'root',
})
export class ExamInCourseStore {
  private examInCourseSubject = new BehaviorSubject<Exam>({
    id: 0,
    userID: 0,
    creationDate: '',
    questionList: [],
  });

  examInCourse$: Observable<Exam> = this.examInCourseSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  createExam(generateExamData: GenerateExamData): Observable<Exam> {
    const { userID } = generateExamData;
    return this.httpClient.post<ExamData>(`${userID}/exam`, generateExamData).pipe(
      catchError(error => {
        const message = 'Could not generate the exam';
        console.error(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData)),
      tap(exam => {
        this.examInCourseSubject.next(exam);
      }),
      shareReplay()
    );
  }

  updateExam(repliedQuestions: Question[]) {
    const { id: examID, userID, creationDate } = this.examInCourseSubject.getValue();

    const updatedExamDTO = this.examMapperService.mapExamToDTO({ examID, userID, creationDate }, repliedQuestions);

    return this.httpClient.patch<Exam>(`${userID}/exam`, updatedExamDTO).pipe(
      catchError(error => {
        const message = 'Could not reply the exam';
        console.error(message, error);
        return throwError(() => new Error(message));
      })
    );
  }

  getNumberOfQuestions(): number {
    return this.examInCourseSubject.getValue().questionList.length;
  }
}
