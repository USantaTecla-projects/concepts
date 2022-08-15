import { Color } from 'src/app/core/enums/color.enum';

export interface ActionButton {
  message?: string;
  isDisabled?: (...args: any[]) => boolean;
  color?: Color;
}
