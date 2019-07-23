import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiPermission } from 'app/shared/model/api-permission.model';
import { ApiPermissionService } from './api-permission.service';
import { ApiPermissionComponent } from './api-permission.component';
import { ApiPermissionDetailComponent } from './api-permission-detail.component';
import { ApiPermissionUpdateComponent } from './api-permission-update.component';
import { ApiPermissionDeletePopupComponent } from './api-permission-delete-dialog.component';
import { IApiPermission } from 'app/shared/model/api-permission.model';

@Injectable({ providedIn: 'root' })
export class ApiPermissionResolve implements Resolve<IApiPermission> {
  constructor(private service: ApiPermissionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiPermission> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiPermission>) => response.ok),
        map((apiPermission: HttpResponse<ApiPermission>) => apiPermission.body)
      );
    }
    return of(new ApiPermission());
  }
}

export const apiPermissionRoute: Routes = [
  {
    path: '',
    component: ApiPermissionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiPermissions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiPermissionDetailComponent,
    resolve: {
      apiPermission: ApiPermissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiPermissions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiPermissionUpdateComponent,
    resolve: {
      apiPermission: ApiPermissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiPermissions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiPermissionUpdateComponent,
    resolve: {
      apiPermission: ApiPermissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiPermissions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiPermissionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiPermissionDeletePopupComponent,
    resolve: {
      apiPermission: ApiPermissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiPermissions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
