import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IApiServiceMethod, ApiServiceMethod } from 'app/shared/model/api-service-method.model';
import { ApiServiceMethodService } from './api-service-method.service';
import { IApiService } from 'app/shared/model/api-service.model';
import { ApiServiceService } from 'app/entities/api-service';
import { IApiMethodResource } from 'app/shared/model/api-method-resource.model';
import { ApiMethodResourceService } from 'app/entities/api-method-resource';
import { IApiPermission } from 'app/shared/model/api-permission.model';
import { ApiPermissionService } from 'app/entities/api-permission';

@Component({
  selector: 'jhi-api-service-method-update',
  templateUrl: './api-service-method-update.component.html'
})
export class ApiServiceMethodUpdateComponent implements OnInit {
  isSaving: boolean;

  apiservices: IApiService[];

  apimethodresources: IApiMethodResource[];

  apipermissions: IApiPermission[];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    version: [null, [Validators.required]],
    path: [null, [Validators.required]],
    method: [null, [Validators.required]],
    description: [],
    apiService: [],
    objects: [],
    methods: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected apiServiceMethodService: ApiServiceMethodService,
    protected apiServiceService: ApiServiceService,
    protected apiMethodResourceService: ApiMethodResourceService,
    protected apiPermissionService: ApiPermissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiServiceMethod }) => {
      this.updateForm(apiServiceMethod);
    });
    this.apiServiceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiService[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiService[]>) => response.body)
      )
      .subscribe((res: IApiService[]) => (this.apiservices = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.apiMethodResourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiMethodResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiMethodResource[]>) => response.body)
      )
      .subscribe((res: IApiMethodResource[]) => (this.apimethodresources = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.apiPermissionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiPermission[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiPermission[]>) => response.body)
      )
      .subscribe((res: IApiPermission[]) => (this.apipermissions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(apiServiceMethod: IApiServiceMethod) {
    this.editForm.patchValue({
      id: apiServiceMethod.id,
      type: apiServiceMethod.type,
      version: apiServiceMethod.version,
      path: apiServiceMethod.path,
      method: apiServiceMethod.method,
      description: apiServiceMethod.description,
      apiService: apiServiceMethod.apiService,
      objects: apiServiceMethod.objects,
      methods: apiServiceMethod.methods
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiServiceMethod = this.createFromForm();
    if (apiServiceMethod.id !== undefined) {
      this.subscribeToSaveResponse(this.apiServiceMethodService.update(apiServiceMethod));
    } else {
      this.subscribeToSaveResponse(this.apiServiceMethodService.create(apiServiceMethod));
    }
  }

  private createFromForm(): IApiServiceMethod {
    return {
      ...new ApiServiceMethod(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      version: this.editForm.get(['version']).value,
      path: this.editForm.get(['path']).value,
      method: this.editForm.get(['method']).value,
      description: this.editForm.get(['description']).value,
      apiService: this.editForm.get(['apiService']).value,
      objects: this.editForm.get(['objects']).value,
      methods: this.editForm.get(['methods']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiServiceMethod>>) {
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

  trackApiServiceById(index: number, item: IApiService) {
    return item.id;
  }

  trackApiMethodResourceById(index: number, item: IApiMethodResource) {
    return item.id;
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
