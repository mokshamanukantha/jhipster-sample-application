/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiServiceMethodDeleteDialogComponent } from 'app/entities/api-service-method/api-service-method-delete-dialog.component';
import { ApiServiceMethodService } from 'app/entities/api-service-method/api-service-method.service';

describe('Component Tests', () => {
  describe('ApiServiceMethod Management Delete Component', () => {
    let comp: ApiServiceMethodDeleteDialogComponent;
    let fixture: ComponentFixture<ApiServiceMethodDeleteDialogComponent>;
    let service: ApiServiceMethodService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiServiceMethodDeleteDialogComponent]
      })
        .overrideTemplate(ApiServiceMethodDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiServiceMethodDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiServiceMethodService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
