import { Color } from 'src/app/core/enums/color.enum';
import { InputType } from 'src/app/core/enums/input-type.enum';
import { FormData } from 'src/app/shared/components/form/interfaces/form-data.interface';

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
