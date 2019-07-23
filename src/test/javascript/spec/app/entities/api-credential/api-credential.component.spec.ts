/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiCredentialComponent } from 'app/entities/api-credential/api-credential.component';
import { ApiCredentialService } from 'app/entities/api-credential/api-credential.service';
import { ApiCredential } from 'app/shared/model/api-credential.model';

describe('Component Tests', () => {
  describe('ApiCredential Management Component', () => {
    let comp: ApiCredentialComponent;
    let fixture: ComponentFixture<ApiCredentialComponent>;
    let service: ApiCredentialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiCredentialComponent],
        providers: []
      })
        .overrideTemplate(ApiCredentialComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiCredentialComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiCredentialService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiCredential(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiCredentials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
