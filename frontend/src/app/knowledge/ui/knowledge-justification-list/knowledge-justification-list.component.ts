import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { State } from 'src/app/shared/utils/state.enum';
import { Justification, JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-justification-list',
  templateUrl: './knowledge-justification-list.component.html',
  styleUrls: ['./knowledge-justification-list.component.scss'],
})
export class KnowledgeJustificationListComponent implements OnInit {
  @Input() justifications: Justification[] | null = [];

  @Output() conceptSelect: EventEmitter<Observable<number>> = new EventEmitter<Observable<number>>();

  editJusitificationForm: FormGroup = new FormGroup({});

  state: string = State.INIT;

  constructor(private formBuilder: FormBuilder, private justificationStore: JustificationStore) {}

  ngOnInit(): void {
    this.editJusitificationForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [null],
      justificationError: [null],
    });

    this.justificationStore.state$.subscribe({
      next: value => (this.state = value),
      error: error => console.log(error),
    });
  }

  disableError(checked: boolean) {
    const errorControl = this.editJusitificationForm.controls['justificationError'];
    checked ? errorControl.disable() : errorControl.enable();
  }

  selectItem(answerID: number) {}

  onJustificationEdit(conceptDTO: any) {}
}
