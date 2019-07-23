import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiPermission, ApiPermission } from 'app/shared/model/api-permission.model';
import { ApiPermissionService } from './api-permission.service';

@Component({
  selector: 'jhi-api-permission-update',
  templateUrl: './api-permission-update.component.html'
})
export class ApiPermissionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    description: []
  });

  constructor(protected apiPermissionService: ApiPermissionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiPermission }) => {
      this.updateForm(apiPermission);
    });
  }

  updateForm(apiPermission: IApiPermission) {
    this.editForm.patchValue({
      id: apiPermission.id,
      code: apiPermission.code,
      description: apiPermission.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiPermission = this.createFromForm();
    if (apiPermission.id !== undefined) {
      this.subscribeToSaveResponse(this.apiPermissionService.update(apiPermission));
    } else {
      this.subscribeToSaveResponse(this.apiPermissionService.create(apiPermission));
    }
  }

  private createFromForm(): IApiPermission {
    return {
      ...new ApiPermission(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiPermission>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
