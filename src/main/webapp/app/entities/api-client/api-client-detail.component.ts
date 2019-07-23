import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiClient } from 'app/shared/model/api-client.model';

@Component({
  selector: 'jhi-api-client-detail',
  templateUrl: './api-client-detail.component.html'
})
export class ApiClientDetailComponent implements OnInit {
  apiClient: IApiClient;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiClient }) => {
      this.apiClient = apiClient;
    });
  }

  previousState() {
    window.history.back();
  }
}
