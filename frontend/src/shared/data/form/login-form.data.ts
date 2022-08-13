import { Color } from 'src/core/enums/color.enum';
import { InputType } from 'src/core/enums/input-type.enum';
import { FormData } from 'src/core/interfaces/form-data.interface';

export const LOGIN_FORM_DATA: FormData = {
  formInputs: [
    {
      label: 'Username',
      placeholder: 'Username',
      required: true,
      icon: 'person',
      type: InputType.TEXT,
    },
    {
      label: 'Password',
      placeholder: 'Password',
      required: true,
      type: InputType.PASSWORD,
    },
  ],
  actionButton: { message: 'Login', color: Color.PRIMARY },
};
