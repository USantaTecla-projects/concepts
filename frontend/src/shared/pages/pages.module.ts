import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ComponentsModule } from 'src/shared/components/components.module';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { AuthenticationPage } from './authentication/authentication.page';
import { ReactiveFormsModule } from '@angular/forms';
import { HomePage } from './home/home.page';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  declarations: [AuthenticationPage, HomePage],
  imports: [
    CommonModule,
    ComponentsModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSnackBarModule,
  ],
  exports: [AuthenticationPage],
})
export class PagesModule {}
