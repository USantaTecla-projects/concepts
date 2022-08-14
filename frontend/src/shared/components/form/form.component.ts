import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActionButton } from 'src/core/interfaces/form/action-button.interface';
import { FormInput } from 'src/core/interfaces/form/form-input.interface';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent<T> implements OnInit {
  @Input() formInputs: FormInput[] = [];

  @Input() actionButton: ActionButton = {};

  @Output() formGroupSubmit: EventEmitter<T> = new EventEmitter<T>();

  form = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    const formGroup = this.formInputs.reduce(
      (acc, input) => ({ ...acc, [input.control]: [''] }),
      {}
    );
    this.form = this.formBuilder.group(formGroup);
  }

  onSubmit() {
    this.formGroupSubmit.emit(this.form.value);
  }

  onFormControlChange(value: string, controlName: string) {
    this.form.controls[controlName].setValue(value);
  }
}
