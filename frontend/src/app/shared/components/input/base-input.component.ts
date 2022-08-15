import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  template: '',
})
export class BaseInputComponent {
  @Output() formControlChange: EventEmitter<string> = new EventEmitter<string>();

  formControl: FormControl = new FormControl('');

  constructor() {}

  onInput() {
    const { value }: { value: string } = this.formControl;
    this.formControlChange.emit(value);
  }
}
