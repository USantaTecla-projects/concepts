import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Justification, JustificationStore } from '../../data-access/justification.store';

@Component({
  selector: 'app-knowledge-justification-list-form',
  templateUrl: './knowledge-justification-list-form.component.html',
  styleUrls: ['./knowledge-justification-list-form.component.scss'],
})
export class KnowledgeJustificationListFormComponent implements OnInit {
  @Input() justification!: Justification;

  justificationForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, private justificationStore: JustificationStore) {}

  ngOnInit(): void {
    this.justificationForm = this.formBuilder.group({
      text: [this.justification.text, [Validators.required]],
      correct: [this.justification.correct],
      justificationError: [{ value: this.justification.error, disabled: this.justification.correct }],
    });
  }

  toggleErrorField(checked: boolean) {
    const errorControl = this.justificationForm.controls['justificationError'];
    checked ? errorControl.disable() : errorControl.enable();
  }

  saveJustification(justificationID: number) {}
}
