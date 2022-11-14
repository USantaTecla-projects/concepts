import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeSpent',
})
export class TimeSpentPipe implements PipeTransform {
  transform(milliseconds: number | undefined): string {
    if (milliseconds) {
      let minutes = Math.floor(milliseconds / 60000).toString();
      if (minutes.length === 1) {
        minutes = `0${minutes}`;
      }

      let seconds = ((milliseconds % 60000) / 1000).toFixed(0).toString();
      if (seconds.length === 1) {
        seconds = `0${seconds}`;
      }

      return `${minutes} minutes & ${seconds} seconds`;
    }

    return 'Cannot parse this number of milliseconds';
  }
}
