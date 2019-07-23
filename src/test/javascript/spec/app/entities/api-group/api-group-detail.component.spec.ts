/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiGroupDetailComponent } from 'app/entities/api-group/api-group-detail.component';
import { ApiGroup } from 'app/shared/model/api-group.model';

describe('Component Tests', () => {
  describe('ApiGroup Management Detail Component', () => {
    let comp: ApiGroupDetailComponent;
    let fixture: ComponentFixture<ApiGroupDetailComponent>;
    const route = ({ data: of({ apiGroup: new ApiGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
