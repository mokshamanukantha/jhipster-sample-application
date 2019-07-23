import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiSession } from 'app/shared/model/api-session.model';
import { ApiSessionService } from './api-session.service';

@Component({
  selector: 'jhi-api-session-delete-dialog',
  templateUrl: './api-session-delete-dialog.component.html'
})
export class ApiSessionDeleteDialogComponent {
  apiSession: IApiSession;

  constructor(
    protected apiSessionService: ApiSessionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiSessionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiSessionListModification',
        content: 'Deleted an apiSession'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-session-delete-popup',
  template: ''
})
export class ApiSessionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiSession }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiSessionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiSession = apiSession;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-session', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-session', { outlets: { popup: null } }]);
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
