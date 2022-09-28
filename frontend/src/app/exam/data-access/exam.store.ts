import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap, catchError, first, Observable, throwError, shareReplay, map } from 'rxjs';
import { ExamData } from '../types/dto/exam.dto';
import { GenerateExamData } from '../types/dto/generate-exam.dto';
import { Exam } from '../types/model/exam.model';
import { QuestionMapperService } from './question-mapper.service';

@Injectable({
  providedIn: 'root',
})
export class ExamStore {
  private emptyExamSubject = new BehaviorSubject<Exam>({ questionList: [] });

  emptyExam$: Observable<Exam> = this.emptyExamSubject.asObservable();

  repliedQuestions: any[] = [];

  constructor(private httpClient: HttpClient, private questionMapperService: QuestionMapperService) {}

  generateExam(generateExamData: GenerateExamData): Observable<Exam> {
    return this.httpClient.post<ExamData>('exams/', generateExamData).pipe(
      catchError(error => {
        const message = 'Could not generate the exam';
        console.log(message, error);
        return throwError(() => error);
      }),
      map(examData => this.questionMapperService.mapQuestions(examData)),
      tap(exam => this.emptyExamSubject.next(exam)),
      shareReplay()
    );
  }

  replyQuestion(question: any) {
    this.repliedQuestions.push(question);
    console.log(this.repliedQuestions);
  }
}
