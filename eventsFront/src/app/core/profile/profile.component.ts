import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { User } from 'src/app/models/user-model/user.model';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  name: FormControl = new FormControl('', [Validators.required]);
  surname: FormControl = new FormControl('', [Validators.required]);
  email: FormControl = new FormControl({value: '', disabled: true }, [Validators.required]);
  username: FormControl = new FormControl('', [Validators.required]);
  phone: FormControl = new FormControl('', [Validators.required]);
  profileForm: FormGroup;

  constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private userService: UserService,
        private authService: AuthService,
        private toastr: ToastrService
    ) {
      this.profileForm = this.formBuilder.group({
        name: this.name,
        surname: this.surname,
        phone: this.phone,
        email: this.email,
        username: this.username
      });
  }

  ngOnInit() {
    this.userService.me(this.authService.getToken()).subscribe( (userData: User) => {
      this.name.setValue(userData.name);
      this.surname.setValue(userData.surname);
      this.phone.setValue(userData.phone);
      this.email.setValue(userData.email);
      this.username.setValue(userData.username);
    },
    (err: HttpErrorResponse) => {
      this.toastr.error(err.message);
    });
  }

  onSubmit() {
    const payLoad = new User(
      this.name.value,
      this.surname.value,
      this.email.value,
      this.phone.value,
      this.username.value
    );
    this.userService.updateMyData(payLoad).subscribe( () => {
      this.toastr.success('successfully changed profile data');
      },
      (err: HttpErrorResponse) => {
      this.toastr.error(err.message);
      });
  }

  changePassword() {
    this.router.navigate(['changePassword']);
  }

}
