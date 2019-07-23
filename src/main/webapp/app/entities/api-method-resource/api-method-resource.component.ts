import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';
import { AccountService } from 'app/core';
import { ApiMethodResourceService } from './api-method-resource.service';

@Component({
  selector: 'jhi-api-method-resource',
  templateUrl: './api-method-resource.component.html'
})
export class ApiMethodResourceComponent implements OnInit, OnDestroy {
  apiMethodResources: IApiMethodResource[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiMethodResourceService: ApiMethodResourceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiMethodResourceService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiMethodResource[]>) => res.ok),
        map((res: HttpResponse<IApiMethodResource[]>) => res.body)
      )
      .subscribe(
        (res: IApiMethodResource[]) => {
          this.apiMethodResources = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiMethodResources();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiMethodResource) {
    return item.id;
  }

  registerChangeInApiMethodResources() {
    this.eventSubscriber = this.eventManager.subscribe('apiMethodResourceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
