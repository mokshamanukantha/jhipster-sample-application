/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiMethodResourceDeleteDialogComponent } from 'app/entities/api-method-resource/api-method-resource-delete-dialog.component';
import { ApiMethodResourceService } from 'app/entities/api-method-resource/api-method-resource.service';

describe('Component Tests', () => {
  describe('ApiMethodResource Management Delete Component', () => {
    let comp: ApiMethodResourceDeleteDialogComponent;
    let fixture: ComponentFixture<ApiMethodResourceDeleteDialogComponent>;
    let service: ApiMethodResourceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiMethodResourceDeleteDialogComponent]
      })
        .overrideTemplate(ApiMethodResourceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiMethodResourceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiMethodResourceService);
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
