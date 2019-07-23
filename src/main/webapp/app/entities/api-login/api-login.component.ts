import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiLogin } from 'app/shared/model/api-login.model';
import { AccountService } from 'app/core';
import { ApiLoginService } from './api-login.service';

@Component({
  selector: 'jhi-api-login',
  templateUrl: './api-login.component.html'
})
export class ApiLoginComponent implements OnInit, OnDestroy {
  apiLogins: IApiLogin[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiLoginService: ApiLoginService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiLoginService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiLogin[]>) => res.ok),
        map((res: HttpResponse<IApiLogin[]>) => res.body)
      )
      .subscribe(
        (res: IApiLogin[]) => {
          this.apiLogins = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiLogins();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiLogin) {
    return item.id;
  }

  registerChangeInApiLogins() {
    this.eventSubscriber = this.eventManager.subscribe('apiLoginListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
