/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiConfigurationUpdateComponent } from 'app/entities/api-configuration/api-configuration-update.component';
import { ApiConfigurationService } from 'app/entities/api-configuration/api-configuration.service';
import { ApiConfiguration } from 'app/shared/model/api-configuration.model';

describe('Component Tests', () => {
  describe('ApiConfiguration Management Update Component', () => {
    let comp: ApiConfigurationUpdateComponent;
    let fixture: ComponentFixture<ApiConfigurationUpdateComponent>;
    let service: ApiConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiConfigurationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApiConfigurationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApiConfigurationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiConfigurationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApiConfiguration(123);
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
        const entity = new ApiConfiguration();
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
