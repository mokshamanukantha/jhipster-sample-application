import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiConfiguration } from 'app/shared/model/api-configuration.model';
import { ApiConfigurationService } from './api-configuration.service';

@Component({
  selector: 'jhi-api-configuration-delete-dialog',
  templateUrl: './api-configuration-delete-dialog.component.html'
})
export class ApiConfigurationDeleteDialogComponent {
  apiConfiguration: IApiConfiguration;

  constructor(
    protected apiConfigurationService: ApiConfigurationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.apiConfigurationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'apiConfigurationListModification',
        content: 'Deleted an apiConfiguration'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-api-configuration-delete-popup',
  template: ''
})
export class ApiConfigurationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ apiConfiguration }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApiConfigurationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apiConfiguration = apiConfiguration;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/api-configuration', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/api-configuration', { outlets: { popup: null } }]);
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
