/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiClientComponent } from 'app/entities/api-client/api-client.component';
import { ApiClientService } from 'app/entities/api-client/api-client.service';
import { ApiClient } from 'app/shared/model/api-client.model';

describe('Component Tests', () => {
  describe('ApiClient Management Component', () => {
    let comp: ApiClientComponent;
    let fixture: ComponentFixture<ApiClientComponent>;
    let service: ApiClientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiClientComponent],
        providers: []
      })
        .overrideTemplate(ApiClientComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiClientComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiClientService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiClient(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiClients[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
