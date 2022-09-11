import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AuthPageRoutingModule } from './auth-routing.module';
import { AuthPage } from './auth.page';
import { AuthInterceptor } from '../utils/auth.interceptor';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuthLoginFormModule } from '../ui/auth-login-form/auth-login-form.module';
import { AuthRegisterFormModule } from '../ui/auth-register-form/auth-register-form.module';

@NgModule({
  imports: [CommonModule, SharedModule, AuthPageRoutingModule, AuthLoginFormModule, AuthRegisterFormModule],
  providers: [],
  declarations: [AuthPage],
})
export class AuthPageModule {}