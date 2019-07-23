import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiSession } from 'app/shared/model/api-session.model';
import { ApiSessionService } from './api-session.service';
import { ApiSessionComponent } from './api-session.component';
import { ApiSessionDetailComponent } from './api-session-detail.component';
import { ApiSessionUpdateComponent } from './api-session-update.component';
import { ApiSessionDeletePopupComponent } from './api-session-delete-dialog.component';
import { IApiSession } from 'app/shared/model/api-session.model';

@Injectable({ providedIn: 'root' })
export class ApiSessionResolve implements Resolve<IApiSession> {
  constructor(private service: ApiSessionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiSession> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiSession>) => response.ok),
        map((apiSession: HttpResponse<ApiSession>) => apiSession.body)
      );
    }
    return of(new ApiSession());
  }
}

export const apiSessionRoute: Routes = [
  {
    path: '',
    component: ApiSessionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiSessions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiSessionDetailComponent,
    resolve: {
      apiSession: ApiSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiSessions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiSessionUpdateComponent,
    resolve: {
      apiSession: ApiSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiSessions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiSessionUpdateComponent,
    resolve: {
      apiSession: ApiSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiSessions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiSessionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiSessionDeletePopupComponent,
    resolve: {
      apiSession: ApiSessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiSessions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
