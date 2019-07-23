/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiClientDetailComponent } from 'app/entities/api-client/api-client-detail.component';
import { ApiClient } from 'app/shared/model/api-client.model';

describe('Component Tests', () => {
  describe('ApiClient Management Detail Component', () => {
    let comp: ApiClientDetailComponent;
    let fixture: ComponentFixture<ApiClientDetailComponent>;
    const route = ({ data: of({ apiClient: new ApiClient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiClientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiClientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiClientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiClient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
