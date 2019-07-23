import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApiPermission } from 'app/shared/model/api-permission.model';
import { AccountService } from 'app/core';
import { ApiPermissionService } from './api-permission.service';

@Component({
  selector: 'jhi-api-permission',
  templateUrl: './api-permission.component.html'
})
export class ApiPermissionComponent implements OnInit, OnDestroy {
  apiPermissions: IApiPermission[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected apiPermissionService: ApiPermissionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.apiPermissionService
      .query()
      .pipe(
        filter((res: HttpResponse<IApiPermission[]>) => res.ok),
        map((res: HttpResponse<IApiPermission[]>) => res.body)
      )
      .subscribe(
        (res: IApiPermission[]) => {
          this.apiPermissions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInApiPermissions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IApiPermission) {
    return item.id;
  }

  registerChangeInApiPermissions() {
    this.eventSubscriber = this.eventManager.subscribe('apiPermissionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
