import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiGroup } from 'app/shared/model/api-group.model';
import { ApiGroupService } from './api-group.service';

@Component({
  selector: 'jhi-api-group-delete-dialog',
  templateUrl: './api-group-delete-dialog.component.html'
})
export class ApiGroupDeleteDialogComponent {
  apiGroup: IApiGroup;

  constructor(protected apiGroupService: ApiGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiGroupListModification',
        content: 'Deleted an apiGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-group-delete-popup',
  template: ''
})
export class ApiGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiGroup = apiGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-group', { outlets: { popup: null } }]);
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
