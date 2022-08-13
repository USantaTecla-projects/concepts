import { InputType } from '../enums/input-type.enum';

export interface FormInput {
  label: string;
  placeholder?: string;
  required?: boolean;
  icon?: string;
  type: InputType;
}
