/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiCredentialDetailComponent } from 'app/entities/api-credential/api-credential-detail.component';
import { ApiCredential } from 'app/shared/model/api-credential.model';

describe('Component Tests', () => {
  describe('ApiCredential Management Detail Component', () => {
    let comp: ApiCredentialDetailComponent;
    let fixture: ComponentFixture<ApiCredentialDetailComponent>;
    const route = ({ data: of({ apiCredential: new ApiCredential(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiCredentialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApiCredentialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiCredentialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiCredential).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
