/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiGroupComponent } from 'app/entities/api-group/api-group.component';
import { ApiGroupService } from 'app/entities/api-group/api-group.service';
import { ApiGroup } from 'app/shared/model/api-group.model';

describe('Component Tests', () => {
  describe('ApiGroup Management Component', () => {
    let comp: ApiGroupComponent;
    let fixture: ComponentFixture<ApiGroupComponent>;
    let service: ApiGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiGroupComponent],
        providers: []
      })
        .overrideTemplate(ApiGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApiGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.apiGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
