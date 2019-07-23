import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiServiceMethod } from 'app/shared/model/api-service-method.model';
import { ApiServiceMethodService } from './api-service-method.service';

@Component({
  selector: 'jhi-api-service-method-delete-dialog',
  templateUrl: './api-service-method-delete-dialog.component.html'
})
export class ApiServiceMethodDeleteDialogComponent {
  apiServiceMethod: IApiServiceMethod;

  constructor(
    protected apiServiceMethodService: ApiServiceMethodService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiServiceMethodService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiServiceMethodListModification',
        content: 'Deleted an apiServiceMethod'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-service-method-delete-popup',
  template: ''
})
export class ApiServiceMethodDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiServiceMethod }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiServiceMethodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiServiceMethod = apiServiceMethod;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-service-method', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-service-method', { outlets: { popup: null } }]);
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
