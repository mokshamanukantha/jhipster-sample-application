import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IApiGroup, ApiGroup } from 'app/shared/model/api-group.model';
import { ApiGroupService } from './api-group.service';
import { IApiPermission } from 'app/shared/model/api-permission.model';
import { ApiPermissionService } from 'app/entities/api-permission';

@Component({
  selector: 'jhi-api-group-update',
  templateUrl: './api-group-update.component.html'
})
export class ApiGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  apipermissions: IApiPermission[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    description: [],
    permissions: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected apiGroupService: ApiGroupService,
    protected apiPermissionService: ApiPermissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiGroup }) => {
      this.updateForm(apiGroup);
    });
    this.apiPermissionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiPermission[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiPermission[]>) => response.body)
      )
      .subscribe((res: IApiPermission[]) => (this.apipermissions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(apiGroup: IApiGroup) {
    this.editForm.patchValue({
      id: apiGroup.id,
      code: apiGroup.code,
      description: apiGroup.description,
      permissions: apiGroup.permissions
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiGroup = this.createFromForm();
    if (apiGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.apiGroupService.update(apiGroup));
    } else {
      this.subscribeToSaveResponse(this.apiGroupService.create(apiGroup));
    }
  }

  private createFromForm(): IApiGroup {
    return {
      ...new ApiGroup(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      description: this.editForm.get(['description']).value,
      permissions: this.editForm.get(['permissions']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiGroup>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackApiPermissionById(index: number, item: IApiPermission) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
