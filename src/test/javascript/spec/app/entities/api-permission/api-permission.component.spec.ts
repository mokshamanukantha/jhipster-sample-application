/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiPermissionComponent } from 'app/entities/api-permission/api-permission.component';
import { ApiPermissionService } from 'app/entities/api-permission/api-permission.service';
import { ApiPermission } from 'app/shared/model/api-permission.model';

describe('Component Tests', () => {
  describe('ApiPermission Management Component', () => {
    let comp: ApiPermissionComponent;
    let fixture: ComponentFixture<ApiPermissionComponent>;
    let service: ApiPermissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiPermissionComponent],
        providers: []
      })
        .overrideTemplate(ApiPermissionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiPermissionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiPermissionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiPermission(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiPermissions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
