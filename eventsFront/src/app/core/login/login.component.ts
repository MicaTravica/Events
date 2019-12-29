import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

import { UserService } from '../../services/user-service/user.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) { 
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit() {
  }

  onSubmit(loginData) {
    this.userService.login(loginData);
  }

}
