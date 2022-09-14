import { Component, OnInit } from '@angular/core';
import { ExamStore } from '../../data-access/exam.store';

@Component({
  selector: 'app-exam-in-course',
  templateUrl: './exam-in-course.page.html',
  styleUrls: ['./exam-in-course.page.scss'],
})
export class ExamInCoursePage implements OnInit {
  constructor(public examStore: ExamStore) {}

  ngOnInit(): void {}
}
