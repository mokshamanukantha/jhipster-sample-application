import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApiCredential } from 'app/shared/model/api-credential.model';
import { ApiCredentialService } from './api-credential.service';
import { ApiCredentialComponent } from './api-credential.component';
import { ApiCredentialDetailComponent } from './api-credential-detail.component';
import { ApiCredentialUpdateComponent } from './api-credential-update.component';
import { ApiCredentialDeletePopupComponent } from './api-credential-delete-dialog.component';
import { IApiCredential } from 'app/shared/model/api-credential.model';

@Injectable({ providedIn: 'root' })
export class ApiCredentialResolve implements Resolve<IApiCredential> {
  constructor(private service: ApiCredentialService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApiCredential> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApiCredential>) => response.ok),
        map((apiCredential: HttpResponse<ApiCredential>) => apiCredential.body)
      );
    }
    return of(new ApiCredential());
  }
}

export const apiCredentialRoute: Routes = [
  {
    path: '',
    component: ApiCredentialComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiCredentials'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiCredentialDetailComponent,
    resolve: {
      apiCredential: ApiCredentialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiCredentials'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiCredentialUpdateComponent,
    resolve: {
      apiCredential: ApiCredentialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiCredentials'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiCredentialUpdateComponent,
    resolve: {
      apiCredential: ApiCredentialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiCredentials'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const apiCredentialPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApiCredentialDeletePopupComponent,
    resolve: {
      apiCredential: ApiCredentialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ApiCredentials'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
