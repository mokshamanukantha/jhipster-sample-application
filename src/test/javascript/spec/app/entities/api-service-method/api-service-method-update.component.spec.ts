/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiServiceMethodUpdateComponent } from 'app/entities/api-service-method/api-service-method-update.component';
import { ApiServiceMethodService } from 'app/entities/api-service-method/api-service-method.service';
import { ApiServiceMethod } from 'app/shared/model/api-service-method.model';

describe('Component Tests', () => {
  describe('ApiServiceMethod Management Update Component', () => {
    let comp: ApiServiceMethodUpdateComponent;
    let fixture: ComponentFixture<ApiServiceMethodUpdateComponent>;
    let service: ApiServiceMethodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiServiceMethodUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiServiceMethodUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiServiceMethodUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiServiceMethodService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiServiceMethod(123);
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
        const entity = new ApiServiceMethod();
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
