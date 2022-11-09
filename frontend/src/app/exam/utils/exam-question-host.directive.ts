import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appQuestionHost]',
})
export class ExamQuestionHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
