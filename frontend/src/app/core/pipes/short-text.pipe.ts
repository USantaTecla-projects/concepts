import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'shortText',
})
export class ShortTextPipe implements PipeTransform {
  transform(text: string, maxLength: number): unknown {
    return text.length > maxLength ? `${text.slice(0, maxLength)}...` : text;
  }
}
