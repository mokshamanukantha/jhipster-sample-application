import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiService } from 'app/shared/model/api-service.model';

@Component({
  selector: 'jhi-api-service-detail',
  templateUrl: './api-service-detail.component.html'
})
export class ApiServiceDetailComponent implements OnInit {
  apiService: IApiService;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiService }) => {
      this.apiService = apiService;
    });
  }

  previousState() {
    window.history.back();
  }
}
