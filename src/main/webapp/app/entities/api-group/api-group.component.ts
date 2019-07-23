import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiGroup } from 'app/shared/model/api-group.model';
import { AccountService } from 'app/core';
import { ApiGroupService } from './api-group.service';

@Component({
  selector: 'jhi-api-group',
  templateUrl: './api-group.component.html'
})
export class ApiGroupComponent implements OnInit, OnDestroy {
  apiGroups: IApiGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiGroupService: ApiGroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiGroupService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiGroup[]>) => res.ok),
        map((res: HttpResponse<IApiGroup[]>) => res.body)
      )
      .subscribe(
        (res: IApiGroup[]) => {
          this.apiGroups = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiGroup) {
    return item.id;
  }

  registerChangeInApiGroups() {
    this.eventSubscriber = this.eventManager.subscribe('apiGroupListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
