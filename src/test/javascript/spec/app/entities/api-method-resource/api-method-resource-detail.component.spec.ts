/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiMethodResourceDetailComponent } from 'app/entities/api-method-resource/api-method-resource-detail.component';
import { ApiMethodResource } from 'app/shared/model/api-method-resource.model';

describe('Component Tests', () => {
  describe('ApiMethodResource Management Detail Component', () => {
    let comp: ApiMethodResourceDetailComponent;
    let fixture: ComponentFixture<ApiMethodResourceDetailComponent>;
    const route = ({ data: of({ apiMethodResource: new ApiMethodResource(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiMethodResourceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiMethodResourceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiMethodResourceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiMethodResource).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
