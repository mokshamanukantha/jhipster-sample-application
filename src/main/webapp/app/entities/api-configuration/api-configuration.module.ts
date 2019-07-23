import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiConfigurationComponent,
  ApiConfigurationDetailComponent,
  ApiConfigurationUpdateComponent,
  ApiConfigurationDeletePopupComponent,
  ApiConfigurationDeleteDialogComponent,
  apiConfigurationRoute,
  apiConfigurationPopupRoute
} from './';

const ENTITY_STATES = [...apiConfigurationRoute, ...apiConfigurationPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiConfigurationComponent,
    ApiConfigurationDetailComponent,
    ApiConfigurationUpdateComponent,
    ApiConfigurationDeleteDialogComponent,
    ApiConfigurationDeletePopupComponent
  ],
  entryComponents: [
    ApiConfigurationComponent,
    ApiConfigurationUpdateComponent,
    ApiConfigurationDeleteDialogComponent,
    ApiConfigurationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiConfigurationModule {}
