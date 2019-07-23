import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiResource, ApiResource } from 'app/shared/model/api-resource.model';
import { ApiResourceService } from './api-resource.service';

@Component({
  selector: 'jhi-api-resource-update',
  templateUrl: './api-resource-update.component.html'
})
export class ApiResourceUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: []
  });

  constructor(protected apiResourceService: ApiResourceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiResource }) => {
      this.updateForm(apiResource);
    });
  }

  updateForm(apiResource: IApiResource) {
    this.editForm.patchValue({
      id: apiResource.id,
      name: apiResource.name,
      description: apiResource.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiResource = this.createFromForm();
    if (apiResource.id !== undefined) {
      this.subscribeToSaveResponse(this.apiResourceService.update(apiResource));
    } else {
      this.subscribeToSaveResponse(this.apiResourceService.create(apiResource));
    }
  }

  private createFromForm(): IApiResource {
    return {
      ...new ApiResource(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiResource>>) {
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
