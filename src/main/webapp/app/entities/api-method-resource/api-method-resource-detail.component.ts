import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';

@Component({
  selector: 'jhi-api-method-resource-detail',
  templateUrl: './api-method-resource-detail.component.html'
})
export class ApiMethodResourceDetailComponent implements OnInit {
  apiMethodResource: IApiMethodResource;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiMethodResource }) => {
      this.apiMethodResource = apiMethodResource;
    });
  }

  previousState() {
    window.history.back();
  }
}
