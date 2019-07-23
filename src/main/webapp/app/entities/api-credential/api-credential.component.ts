import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiCredential } from 'app/shared/model/api-credential.model';
import { AccountService } from 'app/core';
import { ApiCredentialService } from './api-credential.service';

@Component({
  selector: 'jhi-api-credential',
  templateUrl: './api-credential.component.html'
})
export class ApiCredentialComponent implements OnInit, OnDestroy {
  apiCredentials: IApiCredential[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiCredentialService: ApiCredentialService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiCredentialService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiCredential[]>) => res.ok),
        map((res: HttpResponse<IApiCredential[]>) => res.body)
      )
      .subscribe(
        (res: IApiCredential[]) => {
          this.apiCredentials = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiCredentials();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiCredential) {
    return item.id;
  }

  registerChangeInApiCredentials() {
    this.eventSubscriber = this.eventManager.subscribe('apiCredentialListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
