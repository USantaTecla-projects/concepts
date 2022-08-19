import { Pipe, PipeTransform } from '@angular/core';
import { FormInput } from 'src/app/shared/components/form/interfaces/form-input.interface';

@Pipe({
  name: 'setValueInForm',
})
export class SetValueInFormPipe implements PipeTransform {
  transform(formInputs: FormInput[], index: number, value: string): FormInput[] {
    formInputs[index].value = value || '';
    return formInputs;
  }
}
