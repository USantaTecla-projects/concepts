import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { User } from 'src/app/auth/types/model/user.model';
import { Exam } from 'src/app/exam/types/model/exam.model';
import { ExamCorrecionInCourseStore } from '../../data-access/exam-correction-in-course.store';

@Component({
  selector: 'app-correction-in-course',
  templateUrl: './correction-in-course.component.html',
  styleUrls: ['./correction-in-course.component.scss'],
})
export class CorrectionInCourseComponent implements OnInit {
  exam$!: Observable<Exam>;

  user$!: Observable<User | null>;

  constructor(private examCorrecionInCourseStore: ExamCorrecionInCourseStore, private authStore: AuthStore) {}

  ngOnInit(): void {
    this.exam$ = this.examCorrecionInCourseStore.examInCourse$;
    this.user$ = this.authStore.user$;
  }
}
