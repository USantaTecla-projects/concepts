import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, tap, Observable, shareReplay, BehaviorSubject, catchError, of, mergeMap } from 'rxjs';
import { AuthResponse } from './dto/auth-response.dto';
import { Credentials } from './dto/credentials.dto';
import { RegisterUserData } from './dto/register-user.dto';
import { Role } from './enum/role.enum';
import { User } from './model/user.model';

@Injectable({ providedIn: 'root' })
export class AuthStore {
  private userSubject = new BehaviorSubject<User | null>(null);

  user$: Observable<User | null> = this.userSubject.asObservable();

  isLoggedIn$!: Observable<boolean>;

  isLoggedOut$!: Observable<boolean>;

  AUTH_DATA: string = 'auth_data';

  constructor(private httpClient: HttpClient) {
    this.isLoggedIn$ = this.user$.pipe(map(user => Boolean(user)));

    this.isLoggedOut$ = this.isLoggedIn$.pipe(map(isLoggedIn => !isLoggedIn));

    const userJSON = localStorage.getItem(this.AUTH_DATA);

    if (userJSON) this.userSubject.next(JSON.parse(userJSON));
  }

  login(credentials: Credentials): Observable<User> {
    return this.httpClient.post<AuthResponse>('auth/login', credentials).pipe(
      catchError((httpError: HttpErrorResponse) => {
        if (httpError.status === 409) throw 'User with this email or username already exists';
        throw 'Unexpected error, try again later';
      }),
      mergeMap(() => this.getUser(credentials.username)),
      tap(user => {
        this.userSubject.next(user);
        localStorage.setItem(this.AUTH_DATA, JSON.stringify(user));
      })
    );
  }

  logout(): void {
    this.userSubject.next(null);
    localStorage.removeItem(this.AUTH_DATA);
  }

  register(registerUserData: RegisterUserData) {
    return this.httpClient.post<User>('users/', registerUserData).pipe(
      catchError((httpError: HttpErrorResponse) => {
        if (httpError.status === 409) throw 'User with this email or username already exists';
        throw 'Unexpected error, try again later';
      })
    );
  }

  checkUserRoleAdmin(): Observable<boolean> {
    return this.user$.pipe(map(user => Boolean(user?.roles.includes(Role.Teacher))));
  }

  private getUser(username: string): Observable<User> {
    return this.httpClient.get<User>(`users/${username}`).pipe(
      catchError((httpError: HttpErrorResponse) => {
        if (httpError.status === 404) throw 'User with this username has not been found';
        throw 'Unexpected error, try again later';
      })
    );
  }
}
