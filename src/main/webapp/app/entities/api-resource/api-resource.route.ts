import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiResource } from 'app/shared/model/api-resource.model';
import { ApiResourceService } from './api-resource.service';
import { ApiResourceComponent } from './api-resource.component';
import { ApiResourceDetailComponent } from './api-resource-detail.component';
import { ApiResourceUpdateComponent } from './api-resource-update.component';
import { ApiResourceDeletePopupComponent } from './api-resource-delete-dialog.component';
import { IApiResource } from 'app/shared/model/api-resource.model';

@Injectable({ providedIn: 'root' })
export class ApiResourceResolve implements Resolve<IApiResource> {
  constructor(private service: ApiResourceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiResource> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiResource>) => response.ok),
        map((apiResource: HttpResponse<ApiResource>) => apiResource.body)
      );
    }
    return of(new ApiResource());
  }
}

export const apiResourceRoute: Routes = [
  {
    path: '',
    component: ApiResourceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiResources'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiResourceDetailComponent,
    resolve: {
      apiResource: ApiResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiResources'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiResourceUpdateComponent,
    resolve: {
      apiResource: ApiResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiResources'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiResourceUpdateComponent,
    resolve: {
      apiResource: ApiResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiResources'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiResourcePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiResourceDeletePopupComponent,
    resolve: {
      apiResource: ApiResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiResources'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
