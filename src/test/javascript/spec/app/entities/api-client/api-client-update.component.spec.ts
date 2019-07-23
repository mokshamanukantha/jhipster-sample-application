/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiClientUpdateComponent } from 'app/entities/api-client/api-client-update.component';
import { ApiClientService } from 'app/entities/api-client/api-client.service';
import { ApiClient } from 'app/shared/model/api-client.model';

describe('Component Tests', () => {
  describe('ApiClient Management Update Component', () => {
    let comp: ApiClientUpdateComponent;
    let fixture: ComponentFixture<ApiClientUpdateComponent>;
    let service: ApiClientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiClientUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiClientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiClientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiClientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiClient(123);
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
        const entity = new ApiClient();
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
