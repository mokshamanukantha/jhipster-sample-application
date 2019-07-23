/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiPermissionUpdateComponent } from 'app/entities/api-permission/api-permission-update.component';
import { ApiPermissionService } from 'app/entities/api-permission/api-permission.service';
import { ApiPermission } from 'app/shared/model/api-permission.model';

describe('Component Tests', () => {
  describe('ApiPermission Management Update Component', () => {
    let comp: ApiPermissionUpdateComponent;
    let fixture: ComponentFixture<ApiPermissionUpdateComponent>;
    let service: ApiPermissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiPermissionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiPermissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiPermissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiPermissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiPermission(123);
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
        const entity = new ApiPermission();
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
