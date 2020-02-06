import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { newPasswordsMatch } from 'src/app/util/form-validators';
import { UserService } from 'src/app/services/user-service/user.service';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangePassword } from 'src/app/models/change-password-model/change.password.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  passwordForm: FormGroup;
  oldPassword = new FormControl('', [Validators.required, Validators.minLength(8)]);
  password1 = new FormControl('', [Validators.required, Validators.minLength(8)]);
  password2 = new FormControl('', [Validators.required, Validators.minLength(8)]);

  constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private userService: UserService,
        private toastr: ToastrService,
        private authService: AuthService
    ) {}

  // izvuce iz tokena ili odakle vec staru sifru..
  // uporedjivace je sa starom sifrom koju unese korisnik kao da je stara
  ngOnInit() {
    this.passwordForm = this.formBuilder.group({
      oldPassword: this.oldPassword,
      password1: this.password1,
      password2: this.password2
    },
    {
        validators: newPasswordsMatch
    });
  }

  onSubmit() {
    const payLoad = new ChangePassword(this.oldPassword.value, this.password1.value);
    this.userService.changePassword(payLoad)
      .subscribe( (token: string) => {
          localStorage.setItem('token', token);
          this.toastr.success( 'Successfully changed password' );
      },
      (err: HttpErrorResponse) => {
        this.toastr.error(err.message);
      });
  }

}
