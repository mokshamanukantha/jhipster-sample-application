import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiCredential } from 'app/shared/model/api-credential.model';
import { ApiCredentialService } from './api-credential.service';

@Component({
  selector: 'jhi-api-credential-delete-dialog',
  templateUrl: './api-credential-delete-dialog.component.html'
})
export class ApiCredentialDeleteDialogComponent {
  apiCredential: IApiCredential;

  constructor(
    protected apiCredentialService: ApiCredentialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiCredentialService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiCredentialListModification',
        content: 'Deleted an apiCredential'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-credential-delete-popup',
  template: ''
})
export class ApiCredentialDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiCredential }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiCredentialDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiCredential = apiCredential;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-credential', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-credential', { outlets: { popup: null } }]);
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
