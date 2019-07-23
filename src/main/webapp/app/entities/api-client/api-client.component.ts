import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiClient } from 'app/shared/model/api-client.model';
import { AccountService } from 'app/core';
import { ApiClientService } from './api-client.service';

@Component({
  selector: 'jhi-api-client',
  templateUrl: './api-client.component.html'
})
export class ApiClientComponent implements OnInit, OnDestroy {
  apiClients: IApiClient[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiClientService: ApiClientService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiClientService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiClient[]>) => res.ok),
        map((res: HttpResponse<IApiClient[]>) => res.body)
      )
      .subscribe(
        (res: IApiClient[]) => {
          this.apiClients = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiClients();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiClient) {
    return item.id;
  }

  registerChangeInApiClients() {
    this.eventSubscriber = this.eventManager.subscribe('apiClientListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
