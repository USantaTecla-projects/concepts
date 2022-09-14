import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, Observable, throwError, tap, BehaviorSubject, switchMap } from 'rxjs';
import { AuthStore } from '../data-access/auth.store';
import { AuthResponse } from '../interfaces/dto/auth-response.dto';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private httpClient: HttpClient, private router: Router, private authStore: AuthStore) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError((error: HttpErrorResponse) => this.handleError(request, next, error)));
  }

  handleError(request: HttpRequest<unknown>, next: HttpHandler, error: HttpErrorResponse) {
    if (error.status === 401) {
      return this.httpClient.post<AuthResponse>('auth/refresh', {}).pipe(
        switchMap(res => {
          if (!res.error) return next.handle(request);

          this.authStore.logout();
          this.router.navigateByUrl('/auth');
          return throwError(() => new Error('Session expired'));
        })
      );
    }

    return throwError(() => new Error('Session expired'));
  }
}
