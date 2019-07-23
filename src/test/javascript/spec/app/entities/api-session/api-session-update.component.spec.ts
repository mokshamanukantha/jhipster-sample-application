/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiSessionUpdateComponent } from 'app/entities/api-session/api-session-update.component';
import { ApiSessionService } from 'app/entities/api-session/api-session.service';
import { ApiSession } from 'app/shared/model/api-session.model';

describe('Component Tests', () => {
  describe('ApiSession Management Update Component', () => {
    let comp: ApiSessionUpdateComponent;
    let fixture: ComponentFixture<ApiSessionUpdateComponent>;
    let service: ApiSessionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiSessionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiSessionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiSessionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiSessionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiSession(123);
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
        const entity = new ApiSession();
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
