import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AuthPageRoutingModule } from './auth-routing.module';
import { AuthPage } from './auth.page';
import { AuthInterceptor } from '../utils/auth.interceptor';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [AuthPage],
  imports: [CommonModule, SharedModule, AuthPageRoutingModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
})
export class AuthPageModule {}
