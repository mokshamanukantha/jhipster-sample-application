import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiConfiguration } from 'app/shared/model/api-configuration.model';
import { AccountService } from 'app/core';
import { ApiConfigurationService } from './api-configuration.service';

@Component({
  selector: 'jhi-api-configuration',
  templateUrl: './api-configuration.component.html'
})
export class ApiConfigurationComponent implements OnInit, OnDestroy {
  apiConfigurations: IApiConfiguration[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiConfigurationService: ApiConfigurationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiConfigurationService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiConfiguration[]>) => res.ok),
        map((res: HttpResponse<IApiConfiguration[]>) => res.body)
      )
      .subscribe(
        (res: IApiConfiguration[]) => {
          this.apiConfigurations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiConfigurations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiConfiguration) {
    return item.id;
  }

  registerChangeInApiConfigurations() {
    this.eventSubscriber = this.eventManager.subscribe('apiConfigurationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
