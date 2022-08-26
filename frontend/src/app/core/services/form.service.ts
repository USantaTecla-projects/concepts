import { Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class FormService {
  constructor() {}

  setDefaultValues(formInputs: any[], item: any) {
    // for (let i = 0; i < formInputs.length; i++) {
    //   const value = item[formInputs[i].control];
    //   console.log(value);
    //   formInputs[i].textValue = value;
    // }
  }
}
