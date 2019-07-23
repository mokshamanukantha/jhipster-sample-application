import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiLoginComponent,
  ApiLoginDetailComponent,
  ApiLoginUpdateComponent,
  ApiLoginDeletePopupComponent,
  ApiLoginDeleteDialogComponent,
  apiLoginRoute,
  apiLoginPopupRoute
} from './';

const ENTITY_STATES = [...apiLoginRoute, ...apiLoginPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiLoginComponent,
    ApiLoginDetailComponent,
    ApiLoginUpdateComponent,
    ApiLoginDeleteDialogComponent,
    ApiLoginDeletePopupComponent
  ],
  entryComponents: [ApiLoginComponent, ApiLoginUpdateComponent, ApiLoginDeleteDialogComponent, ApiLoginDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiLoginModule {}
