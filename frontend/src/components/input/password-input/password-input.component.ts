import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-password-input',
  templateUrl: './password-input.component.html',
  styleUrls: ['./password-input.component.scss'],
})
export class PasswordInputComponent {
  @Input() label: string | undefined

  @Input() required: boolean | undefined

  hide: boolean = true

  constructor() {}

  ngOnInit(): void {}
}
