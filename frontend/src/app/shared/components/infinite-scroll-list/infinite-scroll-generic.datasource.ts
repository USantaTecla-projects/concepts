import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, firstValueFrom, Observable, Subscription } from 'rxjs';
import { Page } from '../../interfaces/page-response.dto';

export abstract class InfiniteScrollDatasource<T> extends DataSource<T | undefined> {
  private pageSize = 10;
  private length = 10000;
  private cachedData = Array.from<T>({ length: this.pageSize });
  private lastPageFetched = 0;

  private readonly dataStream = new BehaviorSubject<T[]>(this.cachedData);
  private readonly subscription = new Subscription();

  connect(collectionViewer: CollectionViewer): Observable<(T | undefined)[]> {
    this.subscription.add(
      collectionViewer.viewChange.subscribe(range => {
        const pageToLoad = this.getPageForIndex(range.end);
        if (pageToLoad !== this.lastPageFetched || this.lastPageFetched === 0) {
          this.fetchPage(pageToLoad);
        }
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
    if (page === 0) {
      const elementsToRenderDown = await this.getElementsToRenderOnScrollDown(page);
      this.cachedData.splice(page * this.pageSize, this.pageSize, ...elementsToRenderDown);
    }

    if (this.isScrollingDown(page)) {
      this.unloadStartHiddenElements(page);
      const elementsToRenderDown = await this.getElementsToRenderOnScrollDown(page);

      this.hasElementBeenFetchedBefore(page)
        ? this.cachedData.push(...elementsToRenderDown)
        : this.cachedData.splice(page * this.pageSize, this.pageSize, ...elementsToRenderDown);
    }

    if (this.isScrollingUp(page)) {
      this.unloadEndHiddenElements(page);

      if (page > 1) {
        const elementsToRenderOnScrollUp = await this.getElementsToRenderOnScrollUp(page);
        this.cachedData.splice((page - 2) * this.pageSize, this.pageSize, ...elementsToRenderOnScrollUp);
      }
    }

    this.lastPageFetched = page;
    console.log('Cached data');
    console.log(this.cachedData);
    this.dataStream.next(this.cachedData);
  }

  private unloadStartHiddenElements(page: number) {
    const pageToStartUnloading = page - 2;
    if (pageToStartUnloading > 0) {
      const elementsToUnload = pageToStartUnloading * this.pageSize;
      for (let i = 0; i < elementsToUnload; i++) {
        delete this.cachedData[i];
      }
    }
  }

  private async unloadEndHiddenElements(page: number) {
    const pageToStartUnloading = page + 2;
    const indexToStartUnloading = pageToStartUnloading * this.pageSize;
    for (let i = indexToStartUnloading; i < this.length; i++) {
      delete this.cachedData[i];
    }
  }

  private async getElementsToRenderOnScrollUp(page: number) {
    return await this.getElementsToRenderOnScrollDown(page - 2);
  }

  private async getElementsToRenderOnScrollDown(page: number) {
    const fetchedData$ = this.fetchData(page);
    const pageElements = await firstValueFrom(fetchedData$);
    return pageElements.content;
  }

  private hasElementBeenFetchedBefore(page: number) {
    return this.cachedData.length / this.pageSize === page;
  }

  private isScrollingDown(page: number) {
    return page > this.lastPageFetched;
  }

  private isScrollingUp(page: number) {
    return page < this.lastPageFetched;
  }
}
