import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';

import { PasswordInputComponent } from './input/password-input/password-input.component';
import { TextInputComponent } from './input/text-input/text-input.component';
import { FormComponent } from './form/form.component';
import { BaseInputComponent } from './input/base-input.component';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatTreeModule } from '@angular/material/tree';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { TreeComponent } from './tree/tree.component';
import { AccordionListCardComponent } from './accordion-list-card/accordion-list-card.component';
import { AccordionListItemComponent } from './accordion-list-card/accordion-list-item/accordion-list-item.component';
import { CoreModule } from 'src/app/core/core.module';

@NgModule({
  declarations: [
    TextInputComponent,
    PasswordInputComponent,
    FormComponent,
    BaseInputComponent,
    NavbarComponent,
    TreeComponent,
    AccordionListCardComponent,
    AccordionListItemComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatExpansionModule,
    RouterModule,
    LayoutModule,
    MatSidenavModule,
    MatListModule,
    MatTreeModule,
    MatProgressSpinnerModule,
    CoreModule,
  ],
  exports: [
    PasswordInputComponent,
    TextInputComponent,
    FormComponent,
    NavbarComponent,
    TreeComponent,
    AccordionListCardComponent,
  ],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
})
export class ComponentsModule {}
