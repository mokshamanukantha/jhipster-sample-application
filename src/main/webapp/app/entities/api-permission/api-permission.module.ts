import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiPermissionComponent,
  ApiPermissionDetailComponent,
  ApiPermissionUpdateComponent,
  ApiPermissionDeletePopupComponent,
  ApiPermissionDeleteDialogComponent,
  apiPermissionRoute,
  apiPermissionPopupRoute
} from './';

const ENTITY_STATES = [...apiPermissionRoute, ...apiPermissionPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiPermissionComponent,
    ApiPermissionDetailComponent,
    ApiPermissionUpdateComponent,
    ApiPermissionDeleteDialogComponent,
    ApiPermissionDeletePopupComponent
  ],
  entryComponents: [
    ApiPermissionComponent,
    ApiPermissionUpdateComponent,
    ApiPermissionDeleteDialogComponent,
    ApiPermissionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiPermissionModule {}
