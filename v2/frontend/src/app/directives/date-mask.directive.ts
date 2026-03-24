import { Directive, HostListener, ElementRef, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[appDateMask]',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DateMaskDirective),
      multi: true
    }
  ]
})
export class DateMaskDirective implements ControlValueAccessor {
  private onChange: any = () => {};
  private onTouched: any = () => {};

  constructor(private el: ElementRef) {}

  // From model to view (ISO -> DD/MM/YYYY)
  writeValue(value: any): void {
    if (value && typeof value === 'string') {
      const match = value.match(/^(\d{4})-(\d{2})-(\d{2})/);
      if (match) {
        this.el.nativeElement.value = `${match[3]}/${match[2]}/${match[1]}`;
        return;
      }
    }
    this.el.nativeElement.value = value || '';
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  @HostListener('input', ['$event'])
  onInput(event: any): void {
    let val = event.target.value.replace(/\D/g, '');
    if (val.length > 8) val = val.substring(0, 8);

    // Apply Mask DD/MM/YYYY
    let formatted = '';
    if (val.length > 0) {
      formatted = val.substring(0, 2);
      if (val.length > 2) {
        formatted += '/' + val.substring(2, 4);
        if (val.length > 4) {
          formatted += '/' + val.substring(4, 8);
        }
      }
    }
    
    this.el.nativeElement.value = formatted;

    // From view to model (DD/MM/YYYY -> ISO)
    if (val.length === 8) {
      const day = val.substring(0, 2);
      const month = val.substring(2, 4);
      const year = val.substring(4, 8);
      this.onChange(`${year}-${month}-${day}`);
    } else {
      this.onChange(null); // Invalid or incomplete
    }
  }

  @HostListener('blur')
  onBlur(): void {
    this.onTouched();
  }
}
