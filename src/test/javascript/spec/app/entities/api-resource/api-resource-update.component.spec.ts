/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiResourceUpdateComponent } from 'app/entities/api-resource/api-resource-update.component';
import { ApiResourceService } from 'app/entities/api-resource/api-resource.service';
import { ApiResource } from 'app/shared/model/api-resource.model';

describe('Component Tests', () => {
  describe('ApiResource Management Update Component', () => {
    let comp: ApiResourceUpdateComponent;
    let fixture: ComponentFixture<ApiResourceUpdateComponent>;
    let service: ApiResourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiResourceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiResourceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiResourceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiResourceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiResource(123);
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
        const entity = new ApiResource();
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
