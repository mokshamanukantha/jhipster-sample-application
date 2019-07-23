import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiPermission } from 'app/shared/model/api-permission.model';

@Component({
  selector: 'jhi-api-permission-detail',
  templateUrl: './api-permission-detail.component.html'
})
export class ApiPermissionDetailComponent implements OnInit {
  apiPermission: IApiPermission;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiPermission }) => {
      this.apiPermission = apiPermission;
    });
  }

  previousState() {
    window.history.back();
  }
}
