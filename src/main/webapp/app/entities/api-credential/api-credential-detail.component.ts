import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiCredential } from 'app/shared/model/api-credential.model';

@Component({
  selector: 'jhi-api-credential-detail',
  templateUrl: './api-credential-detail.component.html'
})
export class ApiCredentialDetailComponent implements OnInit {
  apiCredential: IApiCredential;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiCredential }) => {
      this.apiCredential = apiCredential;
    });
  }

  previousState() {
    window.history.back();
  }
}
