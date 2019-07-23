import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiService, ApiService } from 'app/shared/model/api-service.model';
import { ApiServiceService } from './api-service.service';

@Component({
  selector: 'jhi-api-service-update',
  templateUrl: './api-service-update.component.html'
})
export class ApiServiceUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    authenticationState: [],
    authorizationState: []
  });

  constructor(protected apiServiceService: ApiServiceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiService }) => {
      this.updateForm(apiService);
    });
  }

  updateForm(apiService: IApiService) {
    this.editForm.patchValue({
      id: apiService.id,
      name: apiService.name,
      description: apiService.description,
      authenticationState: apiService.authenticationState,
      authorizationState: apiService.authorizationState
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiService = this.createFromForm();
    if (apiService.id !== undefined) {
      this.subscribeToSaveResponse(this.apiServiceService.update(apiService));
    } else {
      this.subscribeToSaveResponse(this.apiServiceService.create(apiService));
    }
  }

  private createFromForm(): IApiService {
    return {
      ...new ApiService(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      authenticationState: this.editForm.get(['authenticationState']).value,
      authorizationState: this.editForm.get(['authorizationState']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiService>>) {
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
