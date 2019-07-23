/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiServiceMethodComponent } from 'app/entities/api-service-method/api-service-method.component';
import { ApiServiceMethodService } from 'app/entities/api-service-method/api-service-method.service';
import { ApiServiceMethod } from 'app/shared/model/api-service-method.model';

describe('Component Tests', () => {
  describe('ApiServiceMethod Management Component', () => {
    let comp: ApiServiceMethodComponent;
    let fixture: ComponentFixture<ApiServiceMethodComponent>;
    let service: ApiServiceMethodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiServiceMethodComponent],
        providers: []
      })
        .overrideTemplate(ApiServiceMethodComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiServiceMethodComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiServiceMethodService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiServiceMethod(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiServiceMethods[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
