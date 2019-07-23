/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiResourceDetailComponent } from 'app/entities/api-resource/api-resource-detail.component';
import { ApiResource } from 'app/shared/model/api-resource.model';

describe('Component Tests', () => {
  describe('ApiResource Management Detail Component', () => {
    let comp: ApiResourceDetailComponent;
    let fixture: ComponentFixture<ApiResourceDetailComponent>;
    const route = ({ data: of({ apiResource: new ApiResource(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiResourceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiResourceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiResourceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiResource).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
