import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRegisterFormComponent } from './auth-register-form.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [AuthRegisterFormComponent],
  exports: [AuthRegisterFormComponent],
})
export class AuthRegisterFormModule {}
