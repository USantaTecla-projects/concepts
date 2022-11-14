import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, tap, throwError } from 'rxjs';
import { ExamMapperService } from 'src/app/exam/data-access/exam-mapper.service';
import { ExamData } from 'src/app/shared/types/exam/dto/exam.dto';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { Question } from 'src/app/shared/types/question/model/question.model';
import { CorrectionExamMapperService } from './correction-exam-mapper.service';

@Injectable({
  providedIn: 'root',
})
export class CorrecionInCourseStore {
  private correctionInCourseSubject = new BehaviorSubject<Exam>({
    id: 0,
    userID: 0,
    creationDate: '',
    questionList: [],
    corrected: false,
    mark: '0',
  });

  correctionInCourse$: Observable<Exam> = this.correctionInCourseSubject.asObservable();

  constructor(
    private httpClient: HttpClient,
    private correctionExamMapperService: CorrectionExamMapperService,
    private examMapperService: ExamMapperService
  ) {}

  getExam(userID: number, examID: number) {
    console.log('???');
    return this.httpClient.get<ExamData>(`${userID}/exam/${examID}`).pipe(
      catchError(error => {
        const message = 'Could not get the user exams';
        console.error(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.correctionExamMapperService.mapDTOToExam(examData)),
      tap(res => console.log(res)),
      tap(exam => this.correctionInCourseSubject.next(exam))
    );
  }

  updateExam(correctedQuestions: Question[]) {
    const { id: examID, userID, creationDate } = this.correctionInCourseSubject.getValue();

    const updatedExamDTO = this.examMapperService.mapExamToDTO(
      { examID, userID, creationDate, corrected: true },
      correctedQuestions
    );

    return this.httpClient.patch<Exam>(`${userID}/exam`, updatedExamDTO).pipe(
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
