import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamStore } from 'src/app/exam/data-access/exam.store';

import { Exam } from 'src/app/exam/types/model/exam.model';
import { InfiniteScrollDatasource } from 'src/app/shared/components/infinite-scroll-list/infinite-scroll-generic.datasource';
import { Page } from 'src/app/shared/interfaces/page-response.dto';

@Injectable({ providedIn: 'root' })
export class ExamItemDatasource extends InfiniteScrollDatasource<Exam> {
  constructor(private examStore: ExamStore, private authStore: AuthStore) {
    super();
  }

  fetchData(pageNumber: number): Observable<Page<Exam>> {
    return this.examStore.getUserExams(this.authStore.user$, pageNumber);
  }
}
