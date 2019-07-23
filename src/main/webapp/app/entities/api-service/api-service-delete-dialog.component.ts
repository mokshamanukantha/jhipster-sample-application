import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiService } from 'app/shared/model/api-service.model';
import { ApiServiceService } from './api-service.service';

@Component({
  selector: 'jhi-api-service-delete-dialog',
  templateUrl: './api-service-delete-dialog.component.html'
})
export class ApiServiceDeleteDialogComponent {
  apiService: IApiService;

  constructor(
    protected apiServiceService: ApiServiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiServiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiServiceListModification',
        content: 'Deleted an apiService'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-service-delete-popup',
  template: ''
})
export class ApiServiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiService }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiServiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiService = apiService;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-service', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-service', { outlets: { popup: null } }]);
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
