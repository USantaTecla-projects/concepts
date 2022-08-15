import { InputType } from 'src/app/core/enums/input-type.enum';

export interface FormInput {
  control: string;
  label: string;
  placeholder?: string;
  required?: boolean;
  icon?: string;
  type: InputType;
}
