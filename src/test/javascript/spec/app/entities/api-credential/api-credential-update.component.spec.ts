/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiCredentialUpdateComponent } from 'app/entities/api-credential/api-credential-update.component';
import { ApiCredentialService } from 'app/entities/api-credential/api-credential.service';
import { ApiCredential } from 'app/shared/model/api-credential.model';

describe('Component Tests', () => {
  describe('ApiCredential Management Update Component', () => {
    let comp: ApiCredentialUpdateComponent;
    let fixture: ComponentFixture<ApiCredentialUpdateComponent>;
    let service: ApiCredentialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiCredentialUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiCredentialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiCredentialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiCredentialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiCredential(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiCredential();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
