import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiServiceMethod } from 'app/shared/model/api-service-method.model';

@Component({
  selector: 'jhi-api-service-method-detail',
  templateUrl: './api-service-method-detail.component.html'
})
export class ApiServiceMethodDetailComponent implements OnInit {
  apiServiceMethod: IApiServiceMethod;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiServiceMethod }) => {
      this.apiServiceMethod = apiServiceMethod;
    });
  }

  previousState() {
    window.history.back();
  }
}
