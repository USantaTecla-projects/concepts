import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { AuthenticationInterceptor } from './interceptors/authentication.interceptor'
import { IsLoggedGuard } from './guards/is-logged.guard'

@NgModule({
  declarations: [],
  imports: [CommonModule, HttpClientModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true,
    },
    IsLoggedGuard,
  ],
})
export class CoreModule {}
