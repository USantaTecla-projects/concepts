import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Credentials } from '../../data-access/dto/credentials.dto';

@Component({
  selector: 'app-auth-login-form',
  templateUrl: './auth-login-form.component.html',
  styleUrls: ['./auth-login-form.component.scss'],
})
export class AuthLoginFormComponent implements OnInit {
  @Output() loginUser: EventEmitter<Credentials> = new EventEmitter();

  loginForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  onSubmit(): void {
    const loginFormValue = this.loginForm.value;
    if (loginFormValue) {
      const credentials: Credentials = { ...loginFormValue };
      this.loginUser.emit(credentials);
    }
  }
}
