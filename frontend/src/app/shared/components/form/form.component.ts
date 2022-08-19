import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InputType } from 'src/app/core/enums/input-type.enum';
import { ActionButton } from './interfaces/action-button.interface';
import { FormInput } from './interfaces/form-input.interface';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent<T> implements OnInit {
  @Input() formInputs: FormInput[] = [];

  @Input() actionButton: ActionButton = {};

  @Output() formSubmit: EventEmitter<T> = new EventEmitter<T>();

  form = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    const formGroup = this.formInputs.reduce((acc, input) => {
      if (input.type === InputType.CHECKBOX) return { ...acc, [input.control]: [false] };

      return { ...acc, [input.control]: [input.value] };
    }, {});
    this.form = this.formBuilder.group(formGroup);
  }

  onSubmit() {
    this.formSubmit.emit(this.form.value);
  }

  onFormControlChange(value: string, controlName: string) {
    this.form.controls[controlName].setValue(value);
  }
}
