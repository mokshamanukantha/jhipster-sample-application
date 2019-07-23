import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiSession } from 'app/shared/model/api-session.model';

@Component({
  selector: 'jhi-api-session-detail',
  templateUrl: './api-session-detail.component.html'
})
export class ApiSessionDetailComponent implements OnInit {
  apiSession: IApiSession;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiSession }) => {
      this.apiSession = apiSession;
    });
  }

  previousState() {
    window.history.back();
  }
}
