import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiSessionComponent,
  ApiSessionDetailComponent,
  ApiSessionUpdateComponent,
  ApiSessionDeletePopupComponent,
  ApiSessionDeleteDialogComponent,
  apiSessionRoute,
  apiSessionPopupRoute
} from './';

const ENTITY_STATES = [...apiSessionRoute, ...apiSessionPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiSessionComponent,
    ApiSessionDetailComponent,
    ApiSessionUpdateComponent,
    ApiSessionDeleteDialogComponent,
    ApiSessionDeletePopupComponent
  ],
  entryComponents: [ApiSessionComponent, ApiSessionUpdateComponent, ApiSessionDeleteDialogComponent, ApiSessionDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiSessionModule {}
