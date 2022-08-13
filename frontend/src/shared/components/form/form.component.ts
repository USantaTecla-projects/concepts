import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActionButton } from 'src/core/interfaces/action-button.interface';
import { FormInput } from 'src/core/interfaces/form-input.interface';
import { FormValues } from 'src/core/interfaces/form-values.interface';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent<T> implements OnInit {
  @Input() formInputs: FormInput[] = [];

  @Input() actionButton: ActionButton = {};

  @Output() formGroupSubmit: EventEmitter<FormValues> = new EventEmitter<FormValues>();

  form = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    const formGroup = this.formInputs.reduce((acc, input) => ({ ...acc, [input.label]: [''] }), {});
    this.form = this.formBuilder.group(formGroup);
  }

  onSubmit() {
    this.formGroupSubmit.emit(this.form.value);
  }

  onFormControlChange(value: string, controlName: string) {
    this.form.controls[controlName].setValue(value);
  }
}
