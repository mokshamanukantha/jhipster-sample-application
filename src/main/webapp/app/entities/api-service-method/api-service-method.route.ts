import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiServiceMethod } from 'app/shared/model/api-service-method.model';
import { ApiServiceMethodService } from './api-service-method.service';
import { ApiServiceMethodComponent } from './api-service-method.component';
import { ApiServiceMethodDetailComponent } from './api-service-method-detail.component';
import { ApiServiceMethodUpdateComponent } from './api-service-method-update.component';
import { ApiServiceMethodDeletePopupComponent } from './api-service-method-delete-dialog.component';
import { IApiServiceMethod } from 'app/shared/model/api-service-method.model';

@Injectable({ providedIn: 'root' })
export class ApiServiceMethodResolve implements Resolve<IApiServiceMethod> {
  constructor(private service: ApiServiceMethodService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiServiceMethod> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiServiceMethod>) => response.ok),
        map((apiServiceMethod: HttpResponse<ApiServiceMethod>) => apiServiceMethod.body)
      );
    }
    return of(new ApiServiceMethod());
  }
}

export const apiServiceMethodRoute: Routes = [
  {
    path: '',
    component: ApiServiceMethodComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServiceMethods'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiServiceMethodDetailComponent,
    resolve: {
      apiServiceMethod: ApiServiceMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServiceMethods'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiServiceMethodUpdateComponent,
    resolve: {
      apiServiceMethod: ApiServiceMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServiceMethods'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiServiceMethodUpdateComponent,
    resolve: {
      apiServiceMethod: ApiServiceMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServiceMethods'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiServiceMethodPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiServiceMethodDeletePopupComponent,
    resolve: {
      apiServiceMethod: ApiServiceMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiServiceMethods'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
