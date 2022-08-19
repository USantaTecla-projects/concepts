import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthenticationInterceptor } from './interceptors/authentication.interceptor';
import { IsLoggedGuard } from './guards/is-logged.guard';
import { ApiInterceptor } from './interceptors/api.interceptor';
import { ShortTextPipe } from './pipes/short-text.pipe';
import { SetValueInFormPipe } from './pipes/set-value-in-form.pipe';

@NgModule({
  declarations: [ShortTextPipe, SetValueInFormPipe],
  imports: [CommonModule, HttpClientModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true,
    },
    IsLoggedGuard,
  ],
  exports: [ShortTextPipe, SetValueInFormPipe],
})
export class CoreModule {}
