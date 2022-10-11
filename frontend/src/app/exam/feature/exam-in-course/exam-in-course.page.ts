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
  exam$!: Observable<Exam>;

  replyExam: boolean = false;

  constructor(public examStore: ExamStore) {}

  ngOnInit(): void {
    this.exam$ = this.examStore.exam$;
  }

  onExamReply() {
    this.replyExam = true;
  }
}
