/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiLoginUpdateComponent } from 'app/entities/api-login/api-login-update.component';
import { ApiLoginService } from 'app/entities/api-login/api-login.service';
import { ApiLogin } from 'app/shared/model/api-login.model';

describe('Component Tests', () => {
  describe('ApiLogin Management Update Component', () => {
    let comp: ApiLoginUpdateComponent;
    let fixture: ComponentFixture<ApiLoginUpdateComponent>;
    let service: ApiLoginService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiLoginUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiLoginUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiLoginUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiLoginService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiLogin(123);
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
        const entity = new ApiLogin();
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
