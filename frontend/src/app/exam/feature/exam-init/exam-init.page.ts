import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/shared/utils/snackbar.service';
import { GenerateExamData } from '../../types/dto/generate-exam.dto';
import { ExamStore } from '../../data-access/exam.store';

@Component({
  selector: 'app-exam-init',
  templateUrl: './exam-init.page.html',
  styleUrls: ['./exam-init.page.scss'],
})
export class ExamInitPage implements OnInit {
  constructor(private router: Router, private examStore: ExamStore, private snackbarService: SnackbarService) {}

  ngOnInit(): void {}

  onExamStart(generateExamData: GenerateExamData) {
    this.examStore.generateExam(generateExamData).subscribe({
      next: exam => this.router.navigateByUrl('/exam/in-course'),
      error: (err: Error) => this.snackbarService.openSnackBar(err.message),
    });
  }
}
