import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginDTO } from 'src/app/core/dtos/auth/login.dto';
import { RegisterDTO } from 'src/app/core/dtos/auth/register.dto';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.page.html',
  styleUrls: ['./authentication.page.scss'],
})
export class AuthenticationPage implements OnInit {
  loginForm: FormGroup = new FormGroup({});

  registerForm: FormGroup = new FormGroup({});

  isAuthenticated$: Observable<boolean> | undefined;

  constructor(
    private formBldr: FormBuilder,
    private authenticationSrv: AuthenticationService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBldr.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });

    this.registerForm = this.formBldr.group({
      username: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      password: [null, [Validators.required]],
      repeatedPassword: [null, [Validators.required]],
    });
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', { duration: 5000 });
  }

  login(loginDTO: LoginDTO) {
    this.authenticationSrv.login(loginDTO).subscribe({
      next: () => this.router.navigateByUrl(''),
      error: () => this.openSnackBar('Invalid credentials'),
    });
  }

  register(registerDTO: RegisterDTO) {
    const { password, repeatedPassword } = registerDTO;

    if (password !== repeatedPassword) return this.openSnackBar('Password should coincide');

    this.authenticationSrv.register(registerDTO).subscribe({
      next: () => this.login({ username: registerDTO.username, password: registerDTO.password }),
      error: message => this.openSnackBar(message),
    });
  }
}
