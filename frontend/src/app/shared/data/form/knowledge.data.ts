import { Color } from 'src/app/core/enums/color.enum';
import { InputType } from 'src/app/core/enums/input-type.enum';
import { FormData } from 'src/app/shared/components/form/interfaces/form-data.interface';

export const KNOWLEDGE_FORM_DATA: { [formName: string]: FormData } = {
  concepts: {
    formInputs: [
      {
        control: 'text',
        label: 'Concept',
        placeholder: 'Write the concept definition',
        required: true,
        icon: 'book',
        type: InputType.TEXT,
      },
    ],
    actionButton: {
      message: 'Save',
      color: Color.ACCENT,
    },
  },
  answers: {
    formInputs: [
      {
        control: 'text',
        label: 'Answer',
        placeholder: 'Write the answer for the concept',
        required: true,
        type: InputType.TEXTAREA,
      },
      {
        control: 'isCorrect',
        required: true,
        type: InputType.CHECKBOX,
      },
    ],
    actionButton: {
      message: 'Save',
      color: Color.ACCENT,
    },
  },
  justifications: {
    formInputs: [
      {
        control: 'text',
        label: 'Justification',
        placeholder: 'Write the justification for the answer',
        required: true,
        type: InputType.TEXTAREA,
      },
      {
        control: 'isCorrect',
        required: true,
        type: InputType.CHECKBOX,
      },
    ],
    actionButton: {
      message: 'Save',
      color: Color.ACCENT,
    },
  },
};
