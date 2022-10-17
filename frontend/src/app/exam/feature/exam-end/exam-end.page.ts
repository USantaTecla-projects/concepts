import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-exam-end',
  templateUrl: './exam-end.page.html',
  styleUrls: ['./exam-end.page.scss'],
})
export class ExamEndComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}
}
