/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiConfigurationComponent } from 'app/entities/api-configuration/api-configuration.component';
import { ApiConfigurationService } from 'app/entities/api-configuration/api-configuration.service';
import { ApiConfiguration } from 'app/shared/model/api-configuration.model';

describe('Component Tests', () => {
  describe('ApiConfiguration Management Component', () => {
    let comp: ApiConfigurationComponent;
    let fixture: ComponentFixture<ApiConfigurationComponent>;
    let service: ApiConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiConfigurationComponent],
        providers: []
      })
        .overrideTemplate(ApiConfigurationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiConfigurationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiConfigurationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiConfiguration(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiConfigurations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
