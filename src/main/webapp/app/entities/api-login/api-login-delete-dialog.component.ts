import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiLogin } from 'app/shared/model/api-login.model';
import { ApiLoginService } from './api-login.service';

@Component({
  selector: 'jhi-api-login-delete-dialog',
  templateUrl: './api-login-delete-dialog.component.html'
})
export class ApiLoginDeleteDialogComponent {
  apiLogin: IApiLogin;

  constructor(protected apiLoginService: ApiLoginService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiLoginService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiLoginListModification',
        content: 'Deleted an apiLogin'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-login-delete-popup',
  template: ''
})
export class ApiLoginDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiLogin }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiLoginDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiLogin = apiLogin;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-login', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-login', { outlets: { popup: null } }]);
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
