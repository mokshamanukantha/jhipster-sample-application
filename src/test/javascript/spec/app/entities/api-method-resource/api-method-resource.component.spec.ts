/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiMethodResourceComponent } from 'app/entities/api-method-resource/api-method-resource.component';
import { ApiMethodResourceService } from 'app/entities/api-method-resource/api-method-resource.service';
import { ApiMethodResource } from 'app/shared/model/api-method-resource.model';

describe('Component Tests', () => {
  describe('ApiMethodResource Management Component', () => {
    let comp: ApiMethodResourceComponent;
    let fixture: ComponentFixture<ApiMethodResourceComponent>;
    let service: ApiMethodResourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiMethodResourceComponent],
        providers: []
      })
        .overrideTemplate(ApiMethodResourceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiMethodResourceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiMethodResourceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiMethodResource(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiMethodResources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
