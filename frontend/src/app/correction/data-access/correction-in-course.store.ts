import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, tap, throwError } from 'rxjs';
import { ExamMapperService } from 'src/app/exam/data-access/exam-mapper.service';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { ExamResponse } from 'src/app/shared/types/misc/model/exam-response.model';

@Injectable({
  providedIn: 'root',
})
export class CorrecionInCourseStore {
  private correctionInCourseSubject = new BehaviorSubject<Exam>({
    id: 0,
    userID: 0,
    creationDate: '',
    questionList: [],
    answerList: [],
    corrected: false,
    mark: '0',
  });

  correctionInCourse$: Observable<Exam> = this.correctionInCourseSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  getExam(userID: number, examID: number) {
    console.log('???');
    return this.httpClient.get<ExamData>(`${userID}/exams/${examID}`).pipe(
      catchError(error => {
        const message = 'Could not get the user exams';
        console.error(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData)),
      tap(res => console.log(res)),
      tap(exam => this.correctionInCourseSubject.next(exam))
    );
  }

  updateExam(examResponses: ExamResponse[]) {
    const { id: examID, userID, creationDate } = this.correctionInCourseSubject.getValue();

    const updatedExamDTO = this.examMapperService.mapExamToDTO(
      { examID, userID, creationDate, corrected: true },
      examResponses
    );

    console.log(updatedExamDTO);

    return this.httpClient.patch<Exam>(`${userID}/exams`, updatedExamDTO).pipe(
      catchError(error => {
        const message = 'Could not reply the exam';
        console.error(message, error);
        return throwError(() => new Error(message));
      })
    );
  }

  getNumberOfQuestions(): number {
    return this.correctionInCourseSubject.getValue().questionList.length;
  }
}
