import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';

import { ShortTextPipe } from './utils/short-text.pipe';
import { NavbarModule } from './feature/navbar/navbar.module';

@NgModule({
  imports: [CommonModule, NavbarModule],
  declarations: [ShortTextPipe],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
  exports: [
    ShortTextPipe,
    ReactiveFormsModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatChipsModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
    MatIconModule,
    MatSnackBarModule,
    MatInputModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatDialogModule,
    NavbarModule,
  ],
})
export class SharedModule {}
