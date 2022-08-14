import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

import { PasswordInputComponent } from './input/password-input/password-input.component';
import { TextInputComponent } from './input/text-input/text-input.component';
import { FormComponent } from './form/form.component';
import { BaseInputComponent } from './input/base-input.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [TextInputComponent, PasswordInputComponent, FormComponent, BaseInputComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    RouterModule,
  ],
  exports: [PasswordInputComponent, TextInputComponent, FormComponent],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
})
export class ComponentsModule {}
