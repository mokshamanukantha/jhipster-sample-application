import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiMethodResourceComponent,
  ApiMethodResourceDetailComponent,
  ApiMethodResourceUpdateComponent,
  ApiMethodResourceDeletePopupComponent,
  ApiMethodResourceDeleteDialogComponent,
  apiMethodResourceRoute,
  apiMethodResourcePopupRoute
} from './';

const ENTITY_STATES = [...apiMethodResourceRoute, ...apiMethodResourcePopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiMethodResourceComponent,
    ApiMethodResourceDetailComponent,
    ApiMethodResourceUpdateComponent,
    ApiMethodResourceDeleteDialogComponent,
    ApiMethodResourceDeletePopupComponent
  ],
  entryComponents: [
    ApiMethodResourceComponent,
    ApiMethodResourceUpdateComponent,
    ApiMethodResourceDeleteDialogComponent,
    ApiMethodResourceDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiMethodResourceModule {}
