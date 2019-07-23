import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiService } from 'app/shared/model/api-service.model';
import { ApiServiceService } from './api-service.service';
import { ApiServiceComponent } from './api-service.component';
import { ApiServiceDetailComponent } from './api-service-detail.component';
import { ApiServiceUpdateComponent } from './api-service-update.component';
import { ApiServiceDeletePopupComponent } from './api-service-delete-dialog.component';
import { IApiService } from 'app/shared/model/api-service.model';

@Injectable({ providedIn: 'root' })
export class ApiServiceResolve implements Resolve<IApiService> {
  constructor(private service: ApiServiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiService> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiService>) => response.ok),
        map((apiService: HttpResponse<ApiService>) => apiService.body)
      );
    }
    return of(new ApiService());
  }
}

export const apiServiceRoute: Routes = [
  {
    path: '',
    component: ApiServiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiServiceDetailComponent,
    resolve: {
      apiService: ApiServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiServiceUpdateComponent,
    resolve: {
      apiService: ApiServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiServiceUpdateComponent,
    resolve: {
      apiService: ApiServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServices'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiServicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiServiceDeletePopupComponent,
    resolve: {
      apiService: ApiServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServices'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
