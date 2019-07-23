/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiSessionComponent } from 'app/entities/api-session/api-session.component';
import { ApiSessionService } from 'app/entities/api-session/api-session.service';
import { ApiSession } from 'app/shared/model/api-session.model';

describe('Component Tests', () => {
  describe('ApiSession Management Component', () => {
    let comp: ApiSessionComponent;
    let fixture: ComponentFixture<ApiSessionComponent>;
    let service: ApiSessionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiSessionComponent],
        providers: []
      })
        .overrideTemplate(ApiSessionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiSessionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiSessionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiSession(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiSessions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
