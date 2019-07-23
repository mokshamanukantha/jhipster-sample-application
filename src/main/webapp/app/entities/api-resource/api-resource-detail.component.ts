import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiResource } from 'app/shared/model/api-resource.model';

@Component({
  selector: 'jhi-api-resource-detail',
  templateUrl: './api-resource-detail.component.html'
})
export class ApiResourceDetailComponent implements OnInit {
  apiResource: IApiResource;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiResource }) => {
      this.apiResource = apiResource;
    });
  }

  previousState() {
    window.history.back();
  }
}
