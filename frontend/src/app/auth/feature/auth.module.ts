import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/shared/material.module';

import { AuthLoginFormModule } from '../ui/auth-login-form/auth-login-form.module';
import { AuthRegisterFormModule } from '../ui/auth-register-form/auth-register-form.module';
import { AuthPageRoutingModule } from './auth-routing.module';
import { AuthPage } from './auth.page';

@NgModule({
  imports: [CommonModule, MaterialModule, AuthPageRoutingModule, AuthLoginFormModule, AuthRegisterFormModule],
  providers: [],
  declarations: [AuthPage],
})
export class AuthPageModule {}
