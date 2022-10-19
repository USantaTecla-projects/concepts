import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { User } from 'src/app/auth/types/model/user.model';
import { ExamStore } from 'src/app/exam/data-access/exam.store';
import { Exam } from 'src/app/exam/types/model/exam.model';
import { Page } from 'src/app/shared/interfaces/page-response.dto';
import { SnackbarService } from 'src/app/shared/service/snackbar.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  repliedExamsPage$!: Observable<Page<Exam>>;

  user$!: Observable<User | null>;

  page: number = 0;

  constructor(private examStore: ExamStore, private authStore: AuthStore) {}

  ngOnInit(): void {
    this.user$ = this.authStore.user$;
    this.examStore.getUserExams(this.user$, this.page).subscribe();
    this.repliedExamsPage$ = this.examStore.repliedExamsPage$;
  }
}
