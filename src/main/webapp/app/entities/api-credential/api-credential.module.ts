import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiCredentialComponent,
  ApiCredentialDetailComponent,
  ApiCredentialUpdateComponent,
  ApiCredentialDeletePopupComponent,
  ApiCredentialDeleteDialogComponent,
  apiCredentialRoute,
  apiCredentialPopupRoute
} from './';

const ENTITY_STATES = [...apiCredentialRoute, ...apiCredentialPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiCredentialComponent,
    ApiCredentialDetailComponent,
    ApiCredentialUpdateComponent,
    ApiCredentialDeleteDialogComponent,
    ApiCredentialDeletePopupComponent
  ],
  entryComponents: [
    ApiCredentialComponent,
    ApiCredentialUpdateComponent,
    ApiCredentialDeleteDialogComponent,
    ApiCredentialDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiCredentialModule {}
