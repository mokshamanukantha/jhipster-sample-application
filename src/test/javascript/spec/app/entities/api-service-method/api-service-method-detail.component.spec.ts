/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiServiceMethodDetailComponent } from 'app/entities/api-service-method/api-service-method-detail.component';
import { ApiServiceMethod } from 'app/shared/model/api-service-method.model';

describe('Component Tests', () => {
  describe('ApiServiceMethod Management Detail Component', () => {
    let comp: ApiServiceMethodDetailComponent;
    let fixture: ComponentFixture<ApiServiceMethodDetailComponent>;
    const route = ({ data: of({ apiServiceMethod: new ApiServiceMethod(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiServiceMethodDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiServiceMethodDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiServiceMethodDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiServiceMethod).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
