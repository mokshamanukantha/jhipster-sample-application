import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiResourceComponent,
  ApiResourceDetailComponent,
  ApiResourceUpdateComponent,
  ApiResourceDeletePopupComponent,
  ApiResourceDeleteDialogComponent,
  apiResourceRoute,
  apiResourcePopupRoute
} from './';

const ENTITY_STATES = [...apiResourceRoute, ...apiResourcePopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiResourceComponent,
    ApiResourceDetailComponent,
    ApiResourceUpdateComponent,
    ApiResourceDeleteDialogComponent,
    ApiResourceDeletePopupComponent
  ],
  entryComponents: [ApiResourceComponent, ApiResourceUpdateComponent, ApiResourceDeleteDialogComponent, ApiResourceDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiResourceModule {}
