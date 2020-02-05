import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profileForm;

  constructor(
        private formBuilder: FormBuilder,
        private router: Router
    ) {
    this.profileForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      phone: ['', Validators.required],
      email: ['', Validators.required],
      username: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    console.warn(this.profileForm.value);
  }

  changePassword() {
    this.router.navigate(['changePassword']);
  }

}
