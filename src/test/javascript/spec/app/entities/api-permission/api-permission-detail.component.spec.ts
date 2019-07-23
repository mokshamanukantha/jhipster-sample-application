/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiPermissionDetailComponent } from 'app/entities/api-permission/api-permission-detail.component';
import { ApiPermission } from 'app/shared/model/api-permission.model';

describe('Component Tests', () => {
  describe('ApiPermission Management Detail Component', () => {
    let comp: ApiPermissionDetailComponent;
    let fixture: ComponentFixture<ApiPermissionDetailComponent>;
    const route = ({ data: of({ apiPermission: new ApiPermission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiPermissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiPermissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiPermissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiPermission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
