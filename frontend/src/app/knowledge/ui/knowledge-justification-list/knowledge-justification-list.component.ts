import { Component, Input, OnInit } from '@angular/core';
import { State } from 'src/app/shared/utils/enums/state.enum';
import { Justification, JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-justification-list',
  templateUrl: './knowledge-justification-list.component.html',
  styleUrls: ['./knowledge-justification-list.component.scss'],
})
export class KnowledgeJustificationListComponent implements OnInit {
  @Input() justifications: Justification[] | null = [];

  state: string = State.INIT;

  constructor(private justificationStore: JustificationStore) {}

  ngOnInit(): void {
    this.justificationStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }
}
