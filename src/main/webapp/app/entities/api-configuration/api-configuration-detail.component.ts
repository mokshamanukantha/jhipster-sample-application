import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiConfiguration } from 'app/shared/model/api-configuration.model';

@Component({
  selector: 'jhi-api-configuration-detail',
  templateUrl: './api-configuration-detail.component.html'
})
export class ApiConfigurationDetailComponent implements OnInit {
  apiConfiguration: IApiConfiguration;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiConfiguration }) => {
      this.apiConfiguration = apiConfiguration;
    });
  }

  previousState() {
    window.history.back();
  }
}
