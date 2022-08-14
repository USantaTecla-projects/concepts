import { Color } from 'src/core/enums/color.enum';

export interface ActionButton {
  message?: string;
  isDisabled?: (...args: any[]) => boolean;
  color?: Color;
}
