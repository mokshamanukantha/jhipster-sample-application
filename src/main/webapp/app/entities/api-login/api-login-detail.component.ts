import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiLogin } from 'app/shared/model/api-login.model';

@Component({
  selector: 'jhi-api-login-detail',
  templateUrl: './api-login-detail.component.html'
})
export class ApiLoginDetailComponent implements OnInit {
  apiLogin: IApiLogin;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiLogin }) => {
      this.apiLogin = apiLogin;
    });
  }

  previousState() {
    window.history.back();
  }
}
