import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { User } from 'src/app/auth/types/model/user.model';
import { ExamService } from 'src/app/exam/data-access/exam.service';
import { Exam } from 'src/app/exam/types/model/exam.model';
import { Page } from 'src/app/shared/interfaces/page-response.dto';
import { ExamItemDatasource } from '../data-access/exam-item.datasource';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  hasRepliedExams$!: Observable<Page<Exam>>;

  user$!: Observable<User | null>;

  examItemDatasource!: ExamItemDatasource;

  page = 0;

  constructor(private examService: ExamService, private authStore: AuthStore) {}

  ngOnInit(): void {
    this.user$ = this.authStore.user$;
    this.examService.getUserExams(this.user$, this.page).subscribe();
    this.hasRepliedExams$ = this.examService.getUserExams(this.user$, this.page);
    this.examItemDatasource = new ExamItemDatasource(this.examService, this.authStore);
  }
}
