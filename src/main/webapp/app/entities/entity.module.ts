import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'api-credential',
        loadChildren: './api-credential/api-credential.module#JhipsterSampleApplicationApiCredentialModule'
      },
      {
        path: 'api-service',
        loadChildren: './api-service/api-service.module#JhipsterSampleApplicationApiServiceModule'
      },
      {
        path: 'api-client',
        loadChildren: './api-client/api-client.module#JhipsterSampleApplicationApiClientModule'
      },
      {
        path: 'api-session',
        loadChildren: './api-session/api-session.module#JhipsterSampleApplicationApiSessionModule'
      },
      {
        path: 'api-configuration',
        loadChildren: './api-configuration/api-configuration.module#JhipsterSampleApplicationApiConfigurationModule'
      },
      {
        path: 'api-login',
        loadChildren: './api-login/api-login.module#JhipsterSampleApplicationApiLoginModule'
      },
      {
        path: 'api-service-method',
        loadChildren: './api-service-method/api-service-method.module#JhipsterSampleApplicationApiServiceMethodModule'
      },
      {
        path: 'api-permission',
        loadChildren: './api-permission/api-permission.module#JhipsterSampleApplicationApiPermissionModule'
      },
      {
        path: 'api-group',
        loadChildren: './api-group/api-group.module#JhipsterSampleApplicationApiGroupModule'
      },
      {
        path: 'api-method-resource',
        loadChildren: './api-method-resource/api-method-resource.module#JhipsterSampleApplicationApiMethodResourceModule'
      },
      {
        path: 'api-resource',
        loadChildren: './api-resource/api-resource.module#JhipsterSampleApplicationApiResourceModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
