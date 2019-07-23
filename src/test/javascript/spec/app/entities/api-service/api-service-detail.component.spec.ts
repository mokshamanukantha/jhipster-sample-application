/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiServiceDetailComponent } from 'app/entities/api-service/api-service-detail.component';
import { ApiService } from 'app/shared/model/api-service.model';

describe('Component Tests', () => {
  describe('ApiService Management Detail Component', () => {
    let comp: ApiServiceDetailComponent;
    let fixture: ComponentFixture<ApiServiceDetailComponent>;
    const route = ({ data: of({ apiService: new ApiService(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiServiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiServiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiServiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiService).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
