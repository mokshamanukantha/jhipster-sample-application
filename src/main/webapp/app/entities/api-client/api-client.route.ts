import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiClient } from 'app/shared/model/api-client.model';
import { ApiClientService } from './api-client.service';
import { ApiClientComponent } from './api-client.component';
import { ApiClientDetailComponent } from './api-client-detail.component';
import { ApiClientUpdateComponent } from './api-client-update.component';
import { ApiClientDeletePopupComponent } from './api-client-delete-dialog.component';
import { IApiClient } from 'app/shared/model/api-client.model';

@Injectable({ providedIn: 'root' })
export class ApiClientResolve implements Resolve<IApiClient> {
  constructor(private service: ApiClientService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiClient> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiClient>) => response.ok),
        map((apiClient: HttpResponse<ApiClient>) => apiClient.body)
      );
    }
    return of(new ApiClient());
  }
}

export const apiClientRoute: Routes = [
  {
    path: '',
    component: ApiClientComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiClients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiClientDetailComponent,
    resolve: {
      apiClient: ApiClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiClients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiClientUpdateComponent,
    resolve: {
      apiClient: ApiClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiClients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiClientUpdateComponent,
    resolve: {
      apiClient: ApiClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiClients'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiClientPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiClientDeletePopupComponent,
    resolve: {
      apiClient: ApiClientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiClients'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
