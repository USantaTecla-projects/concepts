import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Justification } from 'src/app/knowledge/data-access/model/justification.model';

@Component({
  selector: 'app-knowledge-justification-create-form',
  templateUrl: './knowledge-justification-create-form.component.html',
  styleUrls: ['./knowledge-justification-create-form.component.scss'],
})
export class KnowledgeJustificationCreateFormComponent implements OnInit {
  @Output() createJustification: EventEmitter<Justification> = new EventEmitter();

  justificationForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.justificationForm = this.formBuilder.group({
      text: [null, [Validators.required]],
      correct: [false],
      error: [null, [Validators.required]],
    });
  }

  toggleErrorField(checked: boolean): void {
    const errorControl = this.justificationForm.controls['error'];
    if (checked) {
      errorControl.disable();
      this.justificationForm.controls['error'].clearValidators();
    } else {
      errorControl.enable();
      this.justificationForm.controls['error'].setValidators(Validators.required);
    }
    this.justificationForm.controls['error'].setValue('');
  }

  onSubmit(): void {
    const justificationFormValue = this.justificationForm.value;
    if (justificationFormValue) {
      const newJustification: Justification = { ...justificationFormValue };
      this.createJustification.emit(newJustification);
    }
  }

  onCancel(): void {
    this.createJustification.emit();
  }
}
