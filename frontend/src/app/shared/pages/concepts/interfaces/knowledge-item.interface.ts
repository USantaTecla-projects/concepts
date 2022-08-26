import { Observable } from 'rxjs';

export interface KnowledgeItem<T> {
  items$: Observable<T[]>;
  state$: Observable<string>;
  selectedItem: number;
}
