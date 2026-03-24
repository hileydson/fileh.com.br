import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateBr',
  standalone: true
})
export class DateBrPipe implements PipeTransform {

  transform(value: any): string {
    if (!value) return '';

    let date: Date | null = null;

    if (value instanceof Date) {
      date = value;
    } else if (typeof value === 'string') {
      // Robust ISO extraction (YYYY-MM-DD or YYYY-MM-DDTHH:mm:ss)
      const isoMatch = value.match(/^(\d{4})-(\d{2})-(\d{2})/);
      if (isoMatch) {
        return `${isoMatch[3]}/${isoMatch[2]}/${isoMatch[1]}`;
      }
      
      // Fallback for other string formats
      date = new Date(value);
    }

    if (date && !isNaN(date.getTime())) {
      const day = String(date.getDate()).padStart(2, '0');
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;
    }

    return value || '';
  }

}
