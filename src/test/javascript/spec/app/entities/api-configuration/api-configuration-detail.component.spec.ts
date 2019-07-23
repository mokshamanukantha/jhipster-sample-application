/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiConfigurationDetailComponent } from 'app/entities/api-configuration/api-configuration-detail.component';
import { ApiConfiguration } from 'app/shared/model/api-configuration.model';

describe('Component Tests', () => {
  describe('ApiConfiguration Management Detail Component', () => {
    let comp: ApiConfigurationDetailComponent;
    let fixture: ComponentFixture<ApiConfigurationDetailComponent>;
    const route = ({ data: of({ apiConfiguration: new ApiConfiguration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiConfigurationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiConfigurationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiConfigurationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiConfiguration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
