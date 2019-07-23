import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiGroupComponent,
  ApiGroupDetailComponent,
  ApiGroupUpdateComponent,
  ApiGroupDeletePopupComponent,
  ApiGroupDeleteDialogComponent,
  apiGroupRoute,
  apiGroupPopupRoute
} from './';

const ENTITY_STATES = [...apiGroupRoute, ...apiGroupPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiGroupComponent,
    ApiGroupDetailComponent,
    ApiGroupUpdateComponent,
    ApiGroupDeleteDialogComponent,
    ApiGroupDeletePopupComponent
  ],
  entryComponents: [ApiGroupComponent, ApiGroupUpdateComponent, ApiGroupDeleteDialogComponent, ApiGroupDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiGroupModule {}
