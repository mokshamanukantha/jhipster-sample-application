import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiClient } from 'app/shared/model/api-client.model';
import { ApiClientService } from './api-client.service';

@Component({
  selector: 'jhi-api-client-delete-dialog',
  templateUrl: './api-client-delete-dialog.component.html'
})
export class ApiClientDeleteDialogComponent {
  apiClient: IApiClient;

  constructor(protected apiClientService: ApiClientService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiClientService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiClientListModification',
        content: 'Deleted an apiClient'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-client-delete-popup',
  template: ''
})
export class ApiClientDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiClient }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiClientDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiClient = apiClient;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-client', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-client', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
