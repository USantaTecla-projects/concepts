import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.scss'],
})
export class TextInputComponent {
  @Input() label: string | undefined

  @Input() placeholder: string | undefined

  @Input() required: boolean | undefined

  @Input() icon: string | undefined

  constructor() {}

  ngOnInit(): void {}
}
