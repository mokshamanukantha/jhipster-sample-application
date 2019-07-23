import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiServiceMethod } from 'app/shared/model/api-service-method.model';
import { AccountService } from 'app/core';
import { ApiServiceMethodService } from './api-service-method.service';

@Component({
  selector: 'jhi-api-service-method',
  templateUrl: './api-service-method.component.html'
})
export class ApiServiceMethodComponent implements OnInit, OnDestroy {
  apiServiceMethods: IApiServiceMethod[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiServiceMethodService: ApiServiceMethodService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiServiceMethodService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiServiceMethod[]>) => res.ok),
        map((res: HttpResponse<IApiServiceMethod[]>) => res.body)
      )
      .subscribe(
        (res: IApiServiceMethod[]) => {
          this.apiServiceMethods = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiServiceMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiServiceMethod) {
    return item.id;
  }

  registerChangeInApiServiceMethods() {
    this.eventSubscriber = this.eventManager.subscribe('apiServiceMethodListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
