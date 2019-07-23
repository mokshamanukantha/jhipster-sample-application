/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiLoginDetailComponent } from 'app/entities/api-login/api-login-detail.component';
import { ApiLogin } from 'app/shared/model/api-login.model';

describe('Component Tests', () => {
  describe('ApiLogin Management Detail Component', () => {
    let comp: ApiLoginDetailComponent;
    let fixture: ComponentFixture<ApiLoginDetailComponent>;
    const route = ({ data: of({ apiLogin: new ApiLogin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiLoginDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiLoginDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiLoginDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiLogin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
