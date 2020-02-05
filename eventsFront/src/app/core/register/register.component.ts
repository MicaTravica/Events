import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { UserService } from '../../services/user-service/user.service'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm;
  submitted;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', Validators.required],
      phone: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  ngOnInit() {
  }

  get form() {
    return this.registerForm.controls;
  }

  onSubmit(registerData) {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }

    this.userService.save(registerData);
  }
}
