import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { UserService } from '../../services/user-service/user.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm;
  submitted;
  error;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) { 
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  get form() { 
    return this.loginForm.controls; 
  }

  setError(status) {
    this.error = status;
  }

  onSubmit(loginData) {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }

    this.userService.login(loginData, this);

  }

}
