import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, firstValueFrom, Observable, Subscription } from 'rxjs';
import { Page } from '../../interfaces/page-response.dto';

export abstract class InfiniteScrollDatasource<T> extends DataSource<T | undefined> {
  private length = 200;
  private pageSize = 10;
  private cachedData = Array.from<T>({ length: this.length });
  private lastPageFetched: number = 0;

  private readonly dataStream = new BehaviorSubject<T[]>(this.cachedData);
  private readonly subscription = new Subscription();

  connect(collectionViewer: CollectionViewer): Observable<(T | undefined)[]> {
    this.subscription.add(
      collectionViewer.viewChange.subscribe(range => {
        const pageToLoad = this.getPageForIndex(range.end - 1);

        this.fetchPage(pageToLoad);
      })
    );

    return this.dataStream;
  }

  disconnect(): void {
    this.subscription.unsubscribe();
  }

  abstract fetchData(pageNumber: number): Observable<Page<T>>;

  private getPageForIndex(index: number): number {
    return Math.floor(index / this.pageSize);
  }

  private async fetchPage(page: number) {
    console.log(this.cachedData);
    console.log({ page });

    this.unloadStartHiddenElements();
    this.unloadEndHiddenElements(page);

    this.lastPageFetched = page;

    const elementsToRenderDown = await this.getElementsToRender(page);

    // this.cachedData.push(...elementsToRenderDown);
    this.cachedData.splice(page * this.pageSize, this.pageSize, ...elementsToRenderDown);
    this.dataStream.next(this.cachedData);
  }

  private unloadStartHiddenElements() {
    const bottomEdgePage = this.lastPageFetched - 2;
    if (bottomEdgePage > 0) {
      const elementsToUnload = bottomEdgePage * this.pageSize;
      for (let i = 0; i < elementsToUnload; i++) {
        this.cachedData[i] = undefined as unknown as T;
      }
    }
  }

  private async unloadEndHiddenElements(page: number) {
    const topEdgePage = this.lastPageFetched + 2;
    const lastPage = this.length / this.pageSize;
    if (topEdgePage < lastPage) {
      const indexToStartUnloading = topEdgePage * this.pageSize;

      for (let i = indexToStartUnloading; i < this.length; i++) {
        this.cachedData[i] = undefined as unknown as T;
      }

      if (page > 1) {
        await this.getElementsToRenderOnScrollUp(page);
      }
    }
  }

  private async getElementsToRenderOnScrollUp(page: number) {
    const elementsToRenderOnScrollUp = await this.getElementsToRender(page - 2);
    this.cachedData.splice((page - 2) * this.pageSize, this.pageSize, ...elementsToRenderOnScrollUp);
  }

  private async getElementsToRender(page: number) {
    const fetchedData$ = this.fetchData(page);
    const pageElements = await firstValueFrom(fetchedData$);
    return pageElements.content;
  }
}
