/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiResourceComponent } from 'app/entities/api-resource/api-resource.component';
import { ApiResourceService } from 'app/entities/api-resource/api-resource.service';
import { ApiResource } from 'app/shared/model/api-resource.model';

describe('Component Tests', () => {
  describe('ApiResource Management Component', () => {
    let comp: ApiResourceComponent;
    let fixture: ComponentFixture<ApiResourceComponent>;
    let service: ApiResourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiResourceComponent],
        providers: []
      })
        .overrideTemplate(ApiResourceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiResourceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiResourceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiResource(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiResources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
