/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiServiceComponent } from 'app/entities/api-service/api-service.component';
import { ApiServiceService } from 'app/entities/api-service/api-service.service';
import { ApiService } from 'app/shared/model/api-service.model';

describe('Component Tests', () => {
  describe('ApiService Management Component', () => {
    let comp: ApiServiceComponent;
    let fixture: ComponentFixture<ApiServiceComponent>;
    let service: ApiServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiServiceComponent],
        providers: []
      })
        .overrideTemplate(ApiServiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiServiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiServiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiService(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
