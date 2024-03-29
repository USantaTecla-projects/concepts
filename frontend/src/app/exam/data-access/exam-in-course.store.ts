import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, shareReplay, tap, throwError } from 'rxjs';
import { ExamMapperService } from 'src/app/shared/service/exam-mapper/exam-mapper.service';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { ExamResponse } from 'src/app/shared/types/misc/model/exam-response.model';
import { GenerateExamData } from '../../shared/types/exam/dto/create-exam.dto';

@Injectable({
  providedIn: 'root',
})
export class ExamInCourseStore {
  private examInCourseSubject = new BehaviorSubject<Exam>({
    id: 0,
    userID: 0,
    creationDate: '',
    questionList: [],
    answerList: [],
    corrected: false,
    mark: '0',
  });

  examInCourse$: Observable<Exam> = this.examInCourseSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  createExam(generateExamData: GenerateExamData): Observable<Exam> {
    const { userID } = generateExamData;

    return this.httpClient.post<ExamData>(`${userID}/exams`, generateExamData).pipe(
      catchError(error => {
        const message = error.error.message ?? 'Could not generate the exam';
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

  updateExam(examResponses: ExamResponse[]) {
    const { id: examID, userID, creationDate, corrected } = this.examInCourseSubject.getValue();

    const updatedExamDTO = this.examMapperService.mapExamToDTO(
      { examID, userID, creationDate, corrected },
      examResponses
    );

    return this.httpClient.put<Exam>(`${userID}/exams`, updatedExamDTO).pipe(
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
