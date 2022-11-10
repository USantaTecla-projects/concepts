import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamService } from 'src/app/exam/data-access/exam.service';
import { User } from 'src/app/shared/types/auth/model/user.model';
import { Exam } from 'src/app/shared/types/exam/model/exam.model';
import { Page } from 'src/app/shared/types/misc/dto/page-response.dto';
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
    this.examService.getUserExams(this.user$, this.page, true).subscribe();
    this.hasRepliedExams$ = this.examService.getUserExams(this.user$, this.page, true);
    this.examItemDatasource = new ExamItemDatasource(this.examService, this.authStore);
  }
}
