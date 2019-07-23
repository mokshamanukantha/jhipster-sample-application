import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiGroup } from 'app/shared/model/api-group.model';
import { ApiGroupService } from './api-group.service';
import { ApiGroupComponent } from './api-group.component';
import { ApiGroupDetailComponent } from './api-group-detail.component';
import { ApiGroupUpdateComponent } from './api-group-update.component';
import { ApiGroupDeletePopupComponent } from './api-group-delete-dialog.component';
import { IApiGroup } from 'app/shared/model/api-group.model';

@Injectable({ providedIn: 'root' })
export class ApiGroupResolve implements Resolve<IApiGroup> {
  constructor(private service: ApiGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiGroup>) => response.ok),
        map((apiGroup: HttpResponse<ApiGroup>) => apiGroup.body)
      );
    }
    return of(new ApiGroup());
  }
}

export const apiGroupRoute: Routes = [
  {
    path: '',
    component: ApiGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiGroupDetailComponent,
    resolve: {
      apiGroup: ApiGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiGroupUpdateComponent,
    resolve: {
      apiGroup: ApiGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiGroupUpdateComponent,
    resolve: {
      apiGroup: ApiGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiGroupDeletePopupComponent,
    resolve: {
      apiGroup: ApiGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
