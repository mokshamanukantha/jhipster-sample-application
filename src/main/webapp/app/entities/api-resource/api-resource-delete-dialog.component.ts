import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiResource } from 'app/shared/model/api-resource.model';
import { ApiResourceService } from './api-resource.service';

@Component({
  selector: 'jhi-api-resource-delete-dialog',
  templateUrl: './api-resource-delete-dialog.component.html'
})
export class ApiResourceDeleteDialogComponent {
  apiResource: IApiResource;

  constructor(
    protected apiResourceService: ApiResourceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiResourceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiResourceListModification',
        content: 'Deleted an apiResource'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-resource-delete-popup',
  template: ''
})
export class ApiResourceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiResource }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiResourceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiResource = apiResource;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-resource', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-resource', { outlets: { popup: null } }]);
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
