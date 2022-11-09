import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appQuestionHost]',
})
export class CorrectionQuestionHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
