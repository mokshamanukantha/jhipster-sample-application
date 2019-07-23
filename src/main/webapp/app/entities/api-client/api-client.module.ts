import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ApiClientComponent,
  ApiClientDetailComponent,
  ApiClientUpdateComponent,
  ApiClientDeletePopupComponent,
  ApiClientDeleteDialogComponent,
  apiClientRoute,
  apiClientPopupRoute
} from './';

const ENTITY_STATES = [...apiClientRoute, ...apiClientPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApiClientComponent,
    ApiClientDetailComponent,
    ApiClientUpdateComponent,
    ApiClientDeleteDialogComponent,
    ApiClientDeletePopupComponent
  ],
  entryComponents: [ApiClientComponent, ApiClientUpdateComponent, ApiClientDeleteDialogComponent, ApiClientDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationApiClientModule {}
