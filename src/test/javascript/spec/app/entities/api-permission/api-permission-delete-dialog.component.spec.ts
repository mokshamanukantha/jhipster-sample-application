/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiPermissionDeleteDialogComponent } from 'app/entities/api-permission/api-permission-delete-dialog.component';
import { ApiPermissionService } from 'app/entities/api-permission/api-permission.service';

describe('Component Tests', () => {
  describe('ApiPermission Management Delete Component', () => {
    let comp: ApiPermissionDeleteDialogComponent;
    let fixture: ComponentFixture<ApiPermissionDeleteDialogComponent>;
    let service: ApiPermissionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiPermissionDeleteDialogComponent]
      })
        .overrideTemplate(ApiPermissionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiPermissionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiPermissionService);
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
