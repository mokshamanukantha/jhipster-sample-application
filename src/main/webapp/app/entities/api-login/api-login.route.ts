import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiLogin } from 'app/shared/model/api-login.model';
import { ApiLoginService } from './api-login.service';
import { ApiLoginComponent } from './api-login.component';
import { ApiLoginDetailComponent } from './api-login-detail.component';
import { ApiLoginUpdateComponent } from './api-login-update.component';
import { ApiLoginDeletePopupComponent } from './api-login-delete-dialog.component';
import { IApiLogin } from 'app/shared/model/api-login.model';

@Injectable({ providedIn: 'root' })
export class ApiLoginResolve implements Resolve<IApiLogin> {
  constructor(private service: ApiLoginService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiLogin> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiLogin>) => response.ok),
        map((apiLogin: HttpResponse<ApiLogin>) => apiLogin.body)
      );
    }
    return of(new ApiLogin());
  }
}

export const apiLoginRoute: Routes = [
  {
    path: '',
    component: ApiLoginComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiLogins'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiLoginDetailComponent,
    resolve: {
      apiLogin: ApiLoginResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiLogins'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiLoginUpdateComponent,
    resolve: {
      apiLogin: ApiLoginResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiLogins'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiLoginUpdateComponent,
    resolve: {
      apiLogin: ApiLoginResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiLogins'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiLoginPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiLoginDeletePopupComponent,
    resolve: {
      apiLogin: ApiLoginResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiLogins'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
