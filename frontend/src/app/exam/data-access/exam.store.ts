import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, shareReplay, tap, throwError } from 'rxjs';
import { ExamData } from '../types/dto/exam.dto';
import { GenerateExamData } from '../types/dto/generate-exam.dto';
import { Exam } from '../types/model/exam.model';
import { Question } from '../types/model/question/question.model';
import { QuestionMapperService } from './question-mapper.service';
import { QuestionReplierService } from './question-replier.service';

@Injectable({
  providedIn: 'root',
})
export class ExamStore {
  private examRepliedSubject = new BehaviorSubject<number>(0);

  private examSubject = new BehaviorSubject<Exam>({ questionList: [] });

  exam$: Observable<Exam> = this.examSubject.asObservable();

  private userID!: number;

  private examID!: number;

  private repliedQuestions!: { [key: string]: any };

  constructor(
    private httpClient: HttpClient,
    private questionMapperService: QuestionMapperService,
    private questionReplierService: QuestionReplierService
  ) {}

  generateExam(generateExamData: GenerateExamData): Observable<Exam> {
    return this.httpClient.post<ExamData>('exams/', generateExamData).pipe(
      catchError(error => {
        const message = 'Could not generate the exam';
        console.log(message, error);
        return throwError(() => new Error(message));
      }),
      map(examData => this.questionMapperService.mapQuestions(examData)),
      tap(exam => {
        this.examSubject.next(exam);
        this.userID = exam.userID!;
      }),
      shareReplay()
    );
  }

  replyQuestion(question: Question, reply: any) {
    console.log(question, reply);
    this.repliedQuestions[question.id] = reply;
    this.examRepliedSubject.next(this.examRepliedSubject.getValue() + 1);
    console.log(this.examRepliedSubject.getValue());
    // const repliedQuestion = this.questionReplierService.storeAnswerOnQuestion(this.userID, question, reply);
    // console.log(repliedQuestion);
  }
}
