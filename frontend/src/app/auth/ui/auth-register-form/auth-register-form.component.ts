import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterUserData } from '../../types/dto/register-user.dto';

@Component({
  selector: 'app-auth-register-form',
  templateUrl: './auth-register-form.component.html',
  styleUrls: ['./auth-register-form.component.scss'],
})
export class AuthRegisterFormComponent implements OnInit {
  @Output() registerUser: EventEmitter<RegisterUserData> = new EventEmitter();

  registerForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      password: [null, [Validators.required]],
      repeatedPassword: [null, [Validators.required]],
    });
  }

  onSubmit(): void {
    const registerFormValue = this.registerForm.value;
    if (registerFormValue) {
      const registerUserData: RegisterUserData = { ...registerFormValue };
      this.registerUser.emit(registerUserData);
    }
  }
}
