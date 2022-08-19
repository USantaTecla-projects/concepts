import { Component, Input, OnInit } from '@angular/core';
import { BaseInputComponent } from '../base-input.component';

@Component({
  selector: 'app-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss'],
})
export class TextInputComponent extends BaseInputComponent implements OnInit {
  @Input() required: boolean = false;

  @Input() label?: string;

  @Input() value?: string | null;

  @Input() placeholder?: string;

  @Input() icon?: string;

  constructor() {
    super();
  }

  ngOnInit() {
    if (this.value) this.formControl.setValue(this.value);
  }
}
