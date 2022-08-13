import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginDTO } from '../dtos/login.dto';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  url: string = environment.BASE_API_URL;

  constructor(private httpClient: HttpClient) {}

  login(loginDTO: LoginDTO) {
    return this.httpClient
      .post(`${this.url}/auth/login`, loginDTO, {
        withCredentials: true,
      })
      .pipe(tap(() => localStorage.setItem('logged', loginDTO.username)));
  }

  logout() {
    return this.httpClient.post(`${this.url}/auth/logout`, {
      withCredentials: true,
    });
  }
}
