import { AbstractControl, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';

export function matchCurrentPassowrd(currentPassword: string): ValidatorFn {
     return (control: AbstractControl): {[key: string]: any} | null => {
        const invalid = currentPassword !== control.value;
        return invalid ? {'badOldPassword': true} : null;
    };
}

export const newPasswordsMatch: ValidatorFn =
    (control: FormGroup): ValidationErrors | null => {
        const password1 = control.get('password1');
        const password2 = control.get('password2');
        return password1 && password2 && password1.value === password2.value ?
        null : { 'passwordsNotMatch': true } ;
};
