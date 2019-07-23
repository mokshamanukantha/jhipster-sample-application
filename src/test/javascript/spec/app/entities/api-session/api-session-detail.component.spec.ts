/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiSessionDetailComponent } from 'app/entities/api-session/api-session-detail.component';
import { ApiSession } from 'app/shared/model/api-session.model';

describe('Component Tests', () => {
  describe('ApiSession Management Detail Component', () => {
    let comp: ApiSessionDetailComponent;
    let fixture: ComponentFixture<ApiSessionDetailComponent>;
    const route = ({ data: of({ apiSession: new ApiSession(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiSessionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiSessionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiSessionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiSession).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
