import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiConfiguration } from 'app/shared/model/api-configuration.model';
import { ApiConfigurationService } from './api-configuration.service';
import { ApiConfigurationComponent } from './api-configuration.component';
import { ApiConfigurationDetailComponent } from './api-configuration-detail.component';
import { ApiConfigurationUpdateComponent } from './api-configuration-update.component';
import { ApiConfigurationDeletePopupComponent } from './api-configuration-delete-dialog.component';
import { IApiConfiguration } from 'app/shared/model/api-configuration.model';

@Injectable({ providedIn: 'root' })
export class ApiConfigurationResolve implements Resolve<IApiConfiguration> {
  constructor(private service: ApiConfigurationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiConfiguration> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiConfiguration>) => response.ok),
        map((apiConfiguration: HttpResponse<ApiConfiguration>) => apiConfiguration.body)
      );
    }
    return of(new ApiConfiguration());
  }
}

export const apiConfigurationRoute: Routes = [
  {
    path: '',
    component: ApiConfigurationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiConfigurationDetailComponent,
    resolve: {
      apiConfiguration: ApiConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiConfigurationUpdateComponent,
    resolve: {
      apiConfiguration: ApiConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiConfigurationUpdateComponent,
    resolve: {
      apiConfiguration: ApiConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiConfigurations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiConfigurationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiConfigurationDeletePopupComponent,
    resolve: {
      apiConfiguration: ApiConfigurationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiConfigurations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
