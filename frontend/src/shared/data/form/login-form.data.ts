import { Color } from 'src/core/enums/color.enum';
import { InputType } from 'src/core/enums/input-type.enum';
import { FormData } from 'src/core/interfaces/form/form-data.interface';

export const LOGIN_FORM_DATA: FormData = {
  formInputs: [
    {
      control: 'username',
      label: 'Username',
      placeholder: 'Username',
      required: true,
      icon: 'person',
      type: InputType.TEXT,
    },
    {
      control: 'password',
      label: 'Password',
      placeholder: 'Password',
      required: true,
      type: InputType.PASSWORD,
    },
  ],
  actionButton: {
    message: 'Login',
    color: Color.PRIMARY,
  },
};
