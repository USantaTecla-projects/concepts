import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, first, Observable, tap } from 'rxjs';
import { AuthResponse } from '../dtos/auth/auth-response.dto';
import { LoginDTO } from '../dtos/auth/login.dto';
import { RegisterDTO } from '../dtos/auth/register.dto';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}

  login(loginDTO: LoginDTO) {
    return this.httpClient.post<AuthResponse>('auth/login', loginDTO).pipe(
      first(),
      tap(() => localStorage.setItem('logged', loginDTO.username))
    );
  }

  logout() {
    return this.httpClient.post('auth/logout', {}).pipe(first());
  }

  register(registerDTO: RegisterDTO) {
    return this.httpClient.post<User>('users/', registerDTO).pipe(
      first(),
      catchError(httpError => this.handleHttpErrors(httpError))
    );
  }

  handleHttpErrors(httpError: HttpErrorResponse): Observable<string> {
    const { status } = httpError;

    if (status === 400) throw 'Form is not filled correctly';

    if (status === 409) throw 'User with this email or username already exists';

    throw 'Unexpected error, try again later';
  }
}
