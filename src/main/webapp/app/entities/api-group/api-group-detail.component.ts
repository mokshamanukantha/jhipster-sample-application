import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiGroup } from 'app/shared/model/api-group.model';

@Component({
  selector: 'jhi-api-group-detail',
  templateUrl: './api-group-detail.component.html'
})
export class ApiGroupDetailComponent implements OnInit {
  apiGroup: IApiGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiGroup }) => {
      this.apiGroup = apiGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
