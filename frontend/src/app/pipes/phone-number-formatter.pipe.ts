import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phoneNumberFormatter'
})
export class PhoneNumberFormatterPipe implements PipeTransform {

  transform(phoneNumber: string): string {
    if (!phoneNumber) return ''; // Controlla se il numero di telefono è vuoto

    // Formatta il numero di telefono
    const firstThreeDigits = phoneNumber.substring(0, 3);
    const remainingDigits = phoneNumber.substring(3);

    return `${firstThreeDigits} ${remainingDigits}`;
  }

}
