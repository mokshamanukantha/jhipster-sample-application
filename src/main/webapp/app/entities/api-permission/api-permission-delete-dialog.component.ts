import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiPermission } from 'app/shared/model/api-permission.model';
import { ApiPermissionService } from './api-permission.service';

@Component({
  selector: 'jhi-api-permission-delete-dialog',
  templateUrl: './api-permission-delete-dialog.component.html'
})
export class ApiPermissionDeleteDialogComponent {
  apiPermission: IApiPermission;

  constructor(
    protected apiPermissionService: ApiPermissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiPermissionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiPermissionListModification',
        content: 'Deleted an apiPermission'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-permission-delete-popup',
  template: ''
})
export class ApiPermissionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiPermission }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiPermissionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiPermission = apiPermission;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-permission', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-permission', { outlets: { popup: null } }]);
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
