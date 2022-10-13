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
export class ExamStore {
  private examSubject = new BehaviorSubject<Exam>({ id: 0, userID: 0, creationDate: '', questionList: [] });

  private userID!: number;

  private examID!: number;

  private creationDate!: string;

  exam$: Observable<Exam> = this.examSubject.asObservable();

  constructor(private httpClient: HttpClient, private examMapperService: ExamMapperService) {}

  createExam(generateExamData: GenerateExamData): Observable<Exam> {
    return this.httpClient.post<ExamData>('exams/', generateExamData).pipe(
      catchError(error => {
        const message = 'Could not generate the exam';
        console.log(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.examMapperService.mapDTOToExam(examData)),
      tap(exam => {
        this.examSubject.next(exam);
        this.userID = exam.userID;
        this.examID = exam.id;
        this.creationDate = exam.creationDate;
      }),
      shareReplay()
    );
  }

  replyExam(repliedQuestions: Question[]) {
    const replyExamDTO = this.examMapperService.mapExamToDTO(
      { examID: this.examID, userID: this.userID, creationDate: this.creationDate },
      repliedQuestions
    );

    console.log(replyExamDTO);

    return this.httpClient.patch<Exam>('exams/', replyExamDTO).pipe(
      catchError(error => {
        const message = 'Could not reply the exam';
        console.log(message, error);
        return throwError(() => new Error(message));
      })
    );
  }

  getNumberOfQuestions() {
    return this.examSubject.getValue().questionList.length;
  }
}
