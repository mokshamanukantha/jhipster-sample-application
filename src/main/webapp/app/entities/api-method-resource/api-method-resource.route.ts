import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiMethodResource } from 'app/shared/model/api-method-resource.model';
import { ApiMethodResourceService } from './api-method-resource.service';
import { ApiMethodResourceComponent } from './api-method-resource.component';
import { ApiMethodResourceDetailComponent } from './api-method-resource-detail.component';
import { ApiMethodResourceUpdateComponent } from './api-method-resource-update.component';
import { ApiMethodResourceDeletePopupComponent } from './api-method-resource-delete-dialog.component';
import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';

@Injectable({ providedIn: 'root' })
export class ApiMethodResourceResolve implements Resolve<IApiMethodResource> {
  constructor(private service: ApiMethodResourceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiMethodResource> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiMethodResource>) => response.ok),
        map((apiMethodResource: HttpResponse<ApiMethodResource>) => apiMethodResource.body)
      );
    }
    return of(new ApiMethodResource());
  }
}

export const apiMethodResourceRoute: Routes = [
  {
    path: '',
    component: ApiMethodResourceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiMethodResources'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiMethodResourceDetailComponent,
    resolve: {
      apiMethodResource: ApiMethodResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiMethodResources'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiMethodResourceUpdateComponent,
    resolve: {
      apiMethodResource: ApiMethodResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiMethodResources'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiMethodResourceUpdateComponent,
    resolve: {
      apiMethodResource: ApiMethodResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiMethodResources'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiMethodResourcePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiMethodResourceDeletePopupComponent,
    resolve: {
      apiMethodResource: ApiMethodResourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiMethodResources'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
