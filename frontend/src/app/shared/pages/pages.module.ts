import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ComponentsModule } from 'src/app/shared/components/components.module';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { AuthenticationPage } from './authentication/authentication.page';
import { ReactiveFormsModule } from '@angular/forms';
import { HomePage } from './home/home.page';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';

import { ConceptsComponent } from './concepts/concepts.page';

@NgModule({
  declarations: [AuthenticationPage, HomePage, ConceptsComponent],
  imports: [
    CommonModule,
    ComponentsModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSnackBarModule,
    MatGridListModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    MatInputModule,
    MatCheckboxModule,
    MatChipsModule,
  ],
  exports: [AuthenticationPage],
})
export class PagesModule {}
