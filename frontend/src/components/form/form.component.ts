import { Component, ContentChild, TemplateRef } from '@angular/core'

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent {
  @ContentChild('formContent') formContent: TemplateRef<any> | undefined

  @ContentChild('formActions') formActions: TemplateRef<any> | undefined

  constructor() {}

  ngOnInit(): void {}
}
