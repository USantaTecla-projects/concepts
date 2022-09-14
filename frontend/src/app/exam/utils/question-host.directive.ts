import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appQuestionHost]',
})
export class QuestionHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
