import { Component, Input, OnInit } from '@angular/core';
import { BaseInputComponent } from '../base-input.component';

@Component({
  selector: 'app-text-area',
  templateUrl: './text-area.component.html',
  styleUrls: ['./text-area.component.scss'],
})
export class TextAreaComponent extends BaseInputComponent implements OnInit {
  @Input() required: boolean = false;

  @Input() label?: string;

  @Input() value?: string;

  @Input() placeholder?: string;

  @Input() icon?: string;

  constructor() {
    super();
  }

  ngOnInit() {
    if (this.value) this.formControl.setValue(this.value);
  }
}
