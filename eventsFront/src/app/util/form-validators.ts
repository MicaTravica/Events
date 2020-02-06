import { AbstractControl, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';


export const newPasswordsMatch: ValidatorFn =
    (control: FormGroup): ValidationErrors | null => {
        const password1 = control.get('password1');
        const password2 = control.get('password2');
        return password1 && password2 && password1.value === password2.value ?
        null : { 'passwordsNotMatch': true } ;
};
