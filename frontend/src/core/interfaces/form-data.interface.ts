import { FormInput } from './form-input.interface';
import { ActionButton } from './action-button.interface';

export interface FormData {
  formInputs: FormInput[];
  actionButton: ActionButton;
}
