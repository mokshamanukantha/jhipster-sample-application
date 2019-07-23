import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiServiceMethodComponent,
  ApiServiceMethodDetailComponent,
  ApiServiceMethodUpdateComponent,
  ApiServiceMethodDeletePopupComponent,
  ApiServiceMethodDeleteDialogComponent,
  apiServiceMethodRoute,
  apiServiceMethodPopupRoute
} from './';

const ENTITY_STATES = [...apiServiceMethodRoute, ...apiServiceMethodPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiServiceMethodComponent,
    ApiServiceMethodDetailComponent,
    ApiServiceMethodUpdateComponent,
    ApiServiceMethodDeleteDialogComponent,
    ApiServiceMethodDeletePopupComponent
  ],
  entryComponents: [
    ApiServiceMethodComponent,
    ApiServiceMethodUpdateComponent,
    ApiServiceMethodDeleteDialogComponent,
    ApiServiceMethodDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiServiceMethodModule {}
