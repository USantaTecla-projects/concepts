import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';

import { MatNativeDateModule } from '@angular/material/core';
import { InfiniteScrollListModule } from './components/infinite-scroll-list/infinite-scroll-list.module';
import { NavbarModule } from './components/navbar/navbar.module';
import { ShortTextPipe } from './utils/short-text.pipe';
import { TimeSpentPipe } from './utils/time-spent.pipe';

@NgModule({
  imports: [CommonModule, NavbarModule],
  declarations: [ShortTextPipe, TimeSpentPipe],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
  exports: [
    ShortTextPipe,
    TimeSpentPipe,
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
    MatPaginatorModule,
    MatRadioModule,
    MatListModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSlideToggleModule,
    NavbarModule,
    InfiniteScrollListModule,
  ],
})
export class SharedModule {}
