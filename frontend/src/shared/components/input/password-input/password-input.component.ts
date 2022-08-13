import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BaseInputComponent } from '../base-input.component';

@Component({
  selector: 'app-password-input',
  templateUrl: './password-input.component.html',
  styleUrls: ['./password-input.component.scss'],
})
export class PasswordInputComponent extends BaseInputComponent {
  @Input() required: boolean = false;

  @Input() label?: string;

  @Input() placeholder?: string;

  hide: boolean = true;

  constructor() {
    super();
  }
}
