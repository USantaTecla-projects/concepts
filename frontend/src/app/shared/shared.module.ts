import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';

import { InfiniteScrollListModule } from './components/infinite-scroll-list/infinite-scroll-list.module';
import { NavbarModule } from './components/navbar/navbar.module';
import { MaterialModule } from './material.module';
import { ShortTextPipe } from './utils/short-text.pipe';
import { TimeSpentPipe } from './utils/time-spent.pipe';

@NgModule({
  imports: [CommonModule, NavbarModule, MaterialModule],
  declarations: [ShortTextPipe, TimeSpentPipe],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
  exports: [ShortTextPipe, TimeSpentPipe, ReactiveFormsModule, NavbarModule, InfiniteScrollListModule],
})
export class SharedModule {}
