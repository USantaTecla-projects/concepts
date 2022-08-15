import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BaseInputComponent } from '../base-input.component';

@Component({
  selector: 'app-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss'],
})
export class TextInputComponent extends BaseInputComponent {
  @Input() required: boolean = false;

  @Input() label?: string;

  @Input() placeholder?: string;

  @Input() icon?: string;

  constructor() {
    super();
  }
}
