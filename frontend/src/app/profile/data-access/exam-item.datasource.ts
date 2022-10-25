import { Observable } from 'rxjs';
import { AppInjector } from 'src/app/app.module';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamStore } from 'src/app/exam/data-access/exam.store';

import { Exam } from 'src/app/exam/types/model/exam.model';
import { InfiniteScrollDatasource } from 'src/app/shared/components/infinite-scroll-list/infinite-scroll-generic.datasource';
import { Page } from 'src/app/shared/interfaces/page-response.dto';

export class ExamItemDatasource extends InfiniteScrollDatasource<Exam> {
  constructor() {
    super();
  }

  fetchData(pageNumber: number): Observable<Page<Exam>> {
    const examStore = AppInjector.get(ExamStore);
    const authStore = AppInjector.get(AuthStore);
    return examStore.getUserExams(authStore.user$, pageNumber);
  }
}
