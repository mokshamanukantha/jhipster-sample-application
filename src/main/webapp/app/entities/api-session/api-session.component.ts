import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiSession } from 'app/shared/model/api-session.model';
import { AccountService } from 'app/core';
import { ApiSessionService } from './api-session.service';

@Component({
  selector: 'jhi-api-session',
  templateUrl: './api-session.component.html'
})
export class ApiSessionComponent implements OnInit, OnDestroy {
  apiSessions: IApiSession[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiSessionService: ApiSessionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiSessionService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiSession[]>) => res.ok),
        map((res: HttpResponse<IApiSession[]>) => res.body)
      )
      .subscribe(
        (res: IApiSession[]) => {
          this.apiSessions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiSessions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiSession) {
    return item.id;
  }

  registerChangeInApiSessions() {
    this.eventSubscriber = this.eventManager.subscribe('apiSessionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
