import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, first, Observable, tap } from 'rxjs';

interface LoginDTO {
  username: string;
  password: string;
}

interface RegisterDTO {
  username: string;
  email: string;
  password: string;
  repeatedPassword: string;
}

interface AuthResponse {
  status: string;
  message: string;
  error: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
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

  // User model?
  register(registerDTO: RegisterDTO) {
    // Create user type
    return this.httpClient.post<any>('users/', registerDTO).pipe(
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
