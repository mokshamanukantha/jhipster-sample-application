import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IApiMethodResource, ApiMethodResource } from 'app/shared/model/api-method-resource.model';
import { ApiMethodResourceService } from './api-method-resource.service';
import { IApiPermission } from 'app/shared/model/api-permission.model';
import { ApiPermissionService } from 'app/entities/api-permission';
import { IApiResource } from 'app/shared/model/api-resource.model';
import { ApiResourceService } from 'app/entities/api-resource';

@Component({
  selector: 'jhi-api-method-resource-update',
  templateUrl: './api-method-resource-update.component.html'
})
export class ApiMethodResourceUpdateComponent implements OnInit {
  isSaving: boolean;

  apipermissions: IApiPermission[];

  apiresources: IApiResource[];

  editForm = this.fb.group({
    id: [],
    restrictedInputPaths: [],
    restrictedOutputPaths: [],
    mask: [null, [Validators.required]],
    enable: [null, [Validators.required]],
    methods: [],
    apiResource: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected apiMethodResourceService: ApiMethodResourceService,
    protected apiPermissionService: ApiPermissionService,
    protected apiResourceService: ApiResourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiMethodResource }) => {
      this.updateForm(apiMethodResource);
    });
    this.apiPermissionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiPermission[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiPermission[]>) => response.body)
      )
      .subscribe((res: IApiPermission[]) => (this.apipermissions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.apiResourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiResource[]>) => response.body)
      )
      .subscribe((res: IApiResource[]) => (this.apiresources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(apiMethodResource: IApiMethodResource) {
    this.editForm.patchValue({
      id: apiMethodResource.id,
      restrictedInputPaths: apiMethodResource.restrictedInputPaths,
      restrictedOutputPaths: apiMethodResource.restrictedOutputPaths,
      mask: apiMethodResource.mask,
      enable: apiMethodResource.enable,
      methods: apiMethodResource.methods,
      apiResource: apiMethodResource.apiResource
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiMethodResource = this.createFromForm();
    if (apiMethodResource.id !== undefined) {
      this.subscribeToSaveResponse(this.apiMethodResourceService.update(apiMethodResource));
    } else {
      this.subscribeToSaveResponse(this.apiMethodResourceService.create(apiMethodResource));
    }
  }

  private createFromForm(): IApiMethodResource {
    return {
      ...new ApiMethodResource(),
      id: this.editForm.get(['id']).value,
      restrictedInputPaths: this.editForm.get(['restrictedInputPaths']).value,
      restrictedOutputPaths: this.editForm.get(['restrictedOutputPaths']).value,
      mask: this.editForm.get(['mask']).value,
      enable: this.editForm.get(['enable']).value,
      methods: this.editForm.get(['methods']).value,
      apiResource: this.editForm.get(['apiResource']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiMethodResource>>) {
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

  trackApiResourceById(index: number, item: IApiResource) {
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
