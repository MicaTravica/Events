import { AbstractControl, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';


export const newPasswordsMatch: ValidatorFn =
    (control: FormGroup): ValidationErrors | null => {
        const password1 = control.get('password1');
        const password2 = control.get('password2');
        return password1 && password2 && password1.value === password2.value ?
        null : { 'passwordsNotMatch': true } ;
};


export const ticketNumberValidator: ValidatorFn =
    (control: FormGroup): ValidationErrors | null => {
        let valid = false;
        const desiredNumber = control.get('desiredNumber');
        const availableNumber = control.get('availableNumber');
        if ( desiredNumber && availableNumber) {
            const dNumber = desiredNumber.value as number;
            const aNumber = availableNumber.value as number;
            valid = dNumber <= aNumber ? true : false;
        }
        return  valid ? null : { 'ticketNumberValidatorError': true } ;
};
