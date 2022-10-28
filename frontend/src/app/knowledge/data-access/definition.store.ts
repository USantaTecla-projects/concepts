import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, shareReplay, tap, throwError } from 'rxjs';
import { State } from 'src/app/shared/interfaces/enums/state.enum';
import { Definition } from '../types/definition.model';

@Injectable({
  providedIn: 'root',
})
export class DefinitionStore {
  private definitionsListSubject = new BehaviorSubject<Definition[]>([]);

  private stateSubject = new BehaviorSubject<string>(State.INIT);

  definitionsList$: Observable<Definition[]> = this.definitionsListSubject.asObservable();

  state$: Observable<string> = this.stateSubject.asObservable();

  constructor(private httpClient: HttpClient) {}

  createDefinition(conceptID: number, definition: Definition) {
    return this.httpClient.post<Definition>(`concepts/${conceptID}/definitions/`, definition).pipe(
      catchError(error => {
        const message = 'Could not create the definition';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(definition => {
        const definitions = this.definitionsListSubject.getValue();
        const newDefinitions = [...definitions, definition];
        this.definitionsListSubject.next(newDefinitions);
      }),
      shareReplay()
    );
  }

  getDefinitions(conceptID: number) {
    this.stateSubject.next(State.LOADING);

    return this.httpClient.get<Definition[]>(`concepts/${conceptID}/definitions/`).pipe(
      catchError(error => {
        const message = 'Could not load concepts';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(definitions => {
        this.definitionsListSubject.next(definitions);
        definitions.length === 0 ? this.stateSubject.next(State.EMPTY) : this.stateSubject.next(State.NORMAL);
      })
    );
  }

  updateDefinition(conceptID: number, definitionID: number, changes: Partial<Definition>) {
    const definitions = this.definitionsListSubject.getValue();
    const index = definitions.findIndex(definition => definition.id === definitionID);

    const newDefinition = {
      ...definitions[index],
      ...changes,
    };

    const newDefinitions = [...definitions];
    newDefinitions[index] = newDefinition;

    this.definitionsListSubject.next(newDefinitions);

    return this.httpClient.put(`concepts/${conceptID}/definitions/${definitionID}`, newDefinition).pipe(
      catchError(error => {
        const message = 'Could not update the definition';
        console.log(message, error);
        return throwError(() => error);
      }),
      shareReplay()
    );
  }

  deleteDefinition(conceptID: number, definitionID: number) {
    const definitions = this.definitionsListSubject.getValue();

    const newDefinitions = definitions.filter(definition => definition.id !== definitionID);

    this.definitionsListSubject.next(newDefinitions);

    return this.httpClient.delete(`concepts/${conceptID}/definitions/${definitionID}`).pipe(
      catchError(error => {
        const message = 'Could not delete the definition';
        console.log(message, error);
        return throwError(() => error);
      }),
      tap(() => {
        if (newDefinitions.length === 0) this.stateSubject.next(State.EMPTY);
      })
    );
  }

  resetDefinitionList() {
    this.definitionsListSubject.next([]);
    this.stateSubject.next(State.INIT);
  }
}
