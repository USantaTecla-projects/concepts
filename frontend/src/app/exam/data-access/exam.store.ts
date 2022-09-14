import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap, catchError, first, Observable, throwError, shareReplay, map } from 'rxjs';
import { ExamData } from '../interfaces/dto/exam.dto';
import { GenerateExamData } from '../interfaces/dto/generate-exam.dto';
import { Exam } from '../interfaces/model/exam.model';
import { QuestionMapperService } from './question-mapper.service';

@Injectable({
  providedIn: 'root',
})
export class ExamStore {
  private examSubject = new BehaviorSubject<Exam>({ questionList: [] });

  exam$: Observable<Exam> = this.examSubject.asObservable();

  constructor(private httpClient: HttpClient, private questionMapperService: QuestionMapperService) {}

  generateExam(generateExamData: GenerateExamData): Observable<Exam> {
    return this.httpClient.post<ExamData>('exams/', generateExamData).pipe(
      catchError(error => {
        const message = 'Could not generate the exam';
        console.log(message, error);
        return throwError(() => error);
      }),
      map(examData => this.questionMapperService.mapQuestions(examData)),
      tap(exam => this.examSubject.next(exam)),
      shareReplay()
    );
  }
}