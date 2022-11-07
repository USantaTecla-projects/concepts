import { Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { ExamService } from 'src/app/exam/data-access/exam.service';

import { Exam } from 'src/app/exam/types/model/exam.model';
import { InfiniteScrollDatasource } from 'src/app/shared/components/infinite-scroll-list/infinite-scroll-generic.datasource';
import { Page } from 'src/app/shared/interfaces/page-response.dto';

export class ExamItemDatasource extends InfiniteScrollDatasource<Exam> {
  constructor(private examService: ExamService, private authStore: AuthStore) {
    super();
  }

  fetchData(pageNumber: number): Observable<Page<Exam>> {
    return this.examService.getUserExams(this.authStore.user$, pageNumber);
  }
}
