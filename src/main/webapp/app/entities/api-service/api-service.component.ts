import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiService } from 'app/shared/model/api-service.model';
import { AccountService } from 'app/core';
import { ApiServiceService } from './api-service.service';

@Component({
  selector: 'jhi-api-service',
  templateUrl: './api-service.component.html'
})
export class ApiServiceComponent implements OnInit, OnDestroy {
  apiServices: IApiService[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiServiceService: ApiServiceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiServiceService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiService[]>) => res.ok),
        map((res: HttpResponse<IApiService[]>) => res.body)
      )
      .subscribe(
        (res: IApiService[]) => {
          this.apiServices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiServices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiService) {
    return item.id;
  }

  registerChangeInApiServices() {
    this.eventSubscriber = this.eventManager.subscribe('apiServiceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
