import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { newPasswordsMatch, matchCurrentPassowrd } from 'src/app/util/form-validators';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  currentPassword = 'old password';
  passwordForm: FormGroup;
  oldPassword = new FormControl('', [Validators.required, matchCurrentPassowrd(this.currentPassword)]);
  password1 = new FormControl('', [Validators.required, Validators.minLength(8)]);
  password2 = new FormControl('', [Validators.required, Validators.minLength(8)]);

  constructor(
        private formBuilder: FormBuilder,
        private router: Router
    ) {}

  // izvuce iz tokena ili odakle vec staru sifru..
  // uporedjivace je sa starom sifrom koju unese korisnik kao da je stara
  ngOnInit() {
    this.passwordForm = this.formBuilder.group({
      'oldPassword': this.oldPassword,
      'password1': this.password1,
      'password2': this.password2
    },
    {
        validators: newPasswordsMatch
    });
  }

  onSubmit() {
    console.log(this.passwordForm.value);
  }

}
