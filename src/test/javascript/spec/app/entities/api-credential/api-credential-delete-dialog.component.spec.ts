/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ApiCredentialDeleteDialogComponent } from 'app/entities/api-credential/api-credential-delete-dialog.component';
import { ApiCredentialService } from 'app/entities/api-credential/api-credential.service';

describe('Component Tests', () => {
  describe('ApiCredential Management Delete Component', () => {
    let comp: ApiCredentialDeleteDialogComponent;
    let fixture: ComponentFixture<ApiCredentialDeleteDialogComponent>;
    let service: ApiCredentialService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ApiCredentialDeleteDialogComponent]
      })
        .overrideTemplate(ApiCredentialDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiCredentialDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApiCredentialService);
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
