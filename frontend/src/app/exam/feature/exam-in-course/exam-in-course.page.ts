import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ExamStore } from '../../data-access/exam.store';
import { Exam } from '../../types/model/exam.model';

@Component({
  selector: 'app-exam-in-course',
  templateUrl: './exam-in-course.page.html',
  styleUrls: ['./exam-in-course.page.scss'],
})
export class ExamInCoursePage implements OnInit {
  emptyExam$!: Observable<Exam>;

  constructor(public examStore: ExamStore) {}

  ngOnInit(): void {
    this.emptyExam$ = this.examStore.emptyExam$;
  }
}
