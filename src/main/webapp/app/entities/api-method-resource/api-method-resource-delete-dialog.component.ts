import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';
import { ApiMethodResourceService } from './api-method-resource.service';

@Component({
  selector: 'jhi-api-method-resource-delete-dialog',
  templateUrl: './api-method-resource-delete-dialog.component.html'
})
export class ApiMethodResourceDeleteDialogComponent {
  apiMethodResource: IApiMethodResource;

  constructor(
    protected apiMethodResourceService: ApiMethodResourceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiMethodResourceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiMethodResourceListModification',
        content: 'Deleted an apiMethodResource'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-method-resource-delete-popup',
  template: ''
})
export class ApiMethodResourceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiMethodResource }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiMethodResourceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiMethodResource = apiMethodResource;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-method-resource', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-method-resource', { outlets: { popup: null } }]);
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
