import { Color } from 'src/core/enums/color.enum';
import { InputType } from 'src/core/enums/input-type.enum';
import { FormData } from 'src/core/interfaces/form-data.interface';

export const REGISTER_FORM_DATA: FormData = {
  formInputs: [
    {
      label: 'Username',
      placeholder: 'Username',
      required: true,
      icon: 'person',
      type: InputType.TEXT,
    },
    {
      label: 'Email',
      placeholder: 'Email',
      required: true,
      icon: 'email',
      type: InputType.TEXT,
    },
    {
      label: 'Password',
      placeholder: 'Password',
      required: true,
      type: InputType.PASSWORD,
    },
    {
      label: 'Repeat password',
      placeholder: 'Repeat password',
      required: true,
      type: InputType.PASSWORD,
    },
  ],
  actionButton: { message: 'Register', color: Color.PRIMARY },
};
