import { Component, ElementRef, forwardRef, HostListener, Input, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, FormsModule } from '@angular/forms';

@Component({
  selector: 'app-date-input',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DateInputComponent),
      multi: true
    }
  ],
  template: `
    <div class="relative flex items-center w-full group">
      <input 
        type="text" 
        [placeholder]="placeholder" 
        [disabled]="disabled"
        [readonly]="readonly"
        [class]="customClass"
        (input)="onTextInput($event)"
        (blur)="onBlur()"
        [value]="displayValue"
        class="input-field pr-10"
      >
      <button 
        type="button" 
        (click)="openPicker()" 
        [disabled]="disabled || readonly"
        class="absolute right-3 p-1 text-slate-400 hover:text-primary-500 transition-colors disabled:opacity-0"
      >
        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
        </svg>
      </button>
      <input 
        #hiddenPicker 
        type="date" 
        [disabled]="disabled || readonly"
        (change)="onPickerChange($event)"
        class="absolute opacity-0 pointer-events-none"
        style="right: 30px; top: 50%; width: 1px; height: 1px; transform: translateY(-50%);"
      >
    </div>
  `
})
export class DateInputComponent implements ControlValueAccessor {
  @Input() placeholder: string = 'DD/MM/AAAA';
  @Input() customClass: string = '';
  @Input() readonly: boolean = false;
  
  @ViewChild('hiddenPicker') hiddenPicker!: ElementRef<HTMLInputElement>;

  displayValue: string = '';
  innerValue: string = ''; // YYYY-MM-DD
  disabled: boolean = false;

  private onChange: any = () => {};
  private onTouched: any = () => {};

  writeValue(value: any): void {
    this.innerValue = value || '';
    this.updateDisplayValue();
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  // Handle manual typing
  onTextInput(event: any): void {
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
    
    this.displayValue = formatted;
    event.target.value = formatted;

    // Convert to ISO for model
    if (val.length === 8) {
      const day = val.substring(0, 2);
      const month = val.substring(2, 4);
      const year = val.substring(4, 8);
      this.innerValue = `${year}-${month}-${day}`;
      this.onChange(this.innerValue);
    } else {
      this.innerValue = '';
      this.onChange(null);
    }
  }

  // Handle calendar selection
  onPickerChange(event: any): void {
    const value = event.target.value; // YYYY-MM-DD
    if (value) {
      this.innerValue = value;
      this.updateDisplayValue();
      this.onChange(this.innerValue);
    }
  }

  openPicker(): void {
    if (this.hiddenPicker && this.hiddenPicker.nativeElement.showPicker) {
      this.hiddenPicker.nativeElement.showPicker();
    } else {
      // Fallback for older browsers
      this.hiddenPicker.nativeElement.click();
    }
  }

  onBlur(): void {
    this.onTouched();
  }

  private updateDisplayValue(): void {
    if (this.innerValue && typeof this.innerValue === 'string') {
      const match = this.innerValue.match(/^(\d{4})-(\d{2})-(\d{2})/);
      if (match) {
        this.displayValue = `${match[3]}/${match[2]}/${match[1]}`;
        return;
      }
    }
    this.displayValue = '';
  }
}
