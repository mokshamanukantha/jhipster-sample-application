import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiServiceComponent,
  ApiServiceDetailComponent,
  ApiServiceUpdateComponent,
  ApiServiceDeletePopupComponent,
  ApiServiceDeleteDialogComponent,
  apiServiceRoute,
  apiServicePopupRoute
} from './';

const ENTITY_STATES = [...apiServiceRoute, ...apiServicePopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiServiceComponent,
    ApiServiceDetailComponent,
    ApiServiceUpdateComponent,
    ApiServiceDeleteDialogComponent,
    ApiServiceDeletePopupComponent
  ],
  entryComponents: [ApiServiceComponent, ApiServiceUpdateComponent, ApiServiceDeleteDialogComponent, ApiServiceDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiServiceModule {}
