import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Justification } from 'src/app/shared/types/justification/justification.model';

@Component({
  selector: 'app-knowledge-justification-update-form',
  templateUrl: './knowledge-justification-update-form.component.html',
  styleUrls: ['./knowledge-justification-update-form.component.scss'],
})
export class KnowledgeJustificationUpdateFormComponent implements OnInit {
  @Input() justification!: Justification;

  @Output() updateJustification: EventEmitter<Justification> = new EventEmitter();

  justificationForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.justificationForm = this.formBuilder.group({
      text: [this.justification.text, [Validators.required]],
      correct: [this.justification.correct],
      error: [
        {
          value: !this.justification.correct ? this.justification.error : '',
          disabled: this.justification.correct,
        },
        [Validators.required],
      ],
    });
  }

  onSubmit(): void {
    const justificationFormValue = this.justificationForm.value;
    if (justificationFormValue) {
      const updatedJustification: Justification = { ...justificationFormValue };
      this.updateJustification.emit(updatedJustification);
    }
  }

  toggleErrorField(checked: boolean): void {
    const errorControl = this.justificationForm.controls['error'];
    checked ? errorControl.disable() : errorControl.enable();
    this.justificationForm.controls['error'].setValue('');
  }
}
