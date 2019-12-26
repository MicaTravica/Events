import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

import { UserService } from '../../services/user-service/user.service'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm;
  
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) { 
    this.registerForm = this.formBuilder.group({
      name: '',
      surname: '',
      email: '',
      phone: '',
      username: '',
      password: ''
    });
  }

  ngOnInit() {
  }

  onSubmit(registerData) {
    this.userService.save(registerData);
  }

  

}
