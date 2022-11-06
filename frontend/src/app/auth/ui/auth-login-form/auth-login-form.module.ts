import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuthLoginFormComponent } from './auth-login-form.component';

@NgModule({
  imports: [CommonModule, SharedModule, MaterialModule],
  declarations: [AuthLoginFormComponent],
  exports: [AuthLoginFormComponent],
})
export class AuthLoginFormModule {}
