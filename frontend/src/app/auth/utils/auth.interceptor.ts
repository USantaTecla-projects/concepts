import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AuthService } from '../data-access/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private AuthSrv: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(catchError(error => this.handleError(error)));
  }

  handleError(error: HttpErrorResponse) {
    const { status } = error;

    if (status === 401) this.AuthSrv.logout();

    return throwError(() => error);
  }
}
