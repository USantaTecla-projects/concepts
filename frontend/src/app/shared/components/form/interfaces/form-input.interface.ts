import { InputType } from 'src/app/core/enums/input-type.enum';

export interface FormInput {
  control: string;
  label?: string;
  placeholder?: string;
  required?: boolean;
  value?: string;
  icon?: string;
  type: InputType;
}
