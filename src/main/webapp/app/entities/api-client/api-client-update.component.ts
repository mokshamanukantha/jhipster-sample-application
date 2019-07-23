import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiClient, ApiClient } from 'app/shared/model/api-client.model';
import { ApiClientService } from './api-client.service';

@Component({
  selector: 'jhi-api-client-update',
  templateUrl: './api-client-update.component.html'
})
export class ApiClientUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clientId: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    cookieEnable: [null, [Validators.required]],
    cookieTTL: [null, [Validators.required]],
    clientCallback: [null, [Validators.required]]
  });

  constructor(protected apiClientService: ApiClientService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiClient }) => {
      this.updateForm(apiClient);
    });
  }

  updateForm(apiClient: IApiClient) {
    this.editForm.patchValue({
      id: apiClient.id,
      clientId: apiClient.clientId,
      name: apiClient.name,
      description: apiClient.description,
      cookieEnable: apiClient.cookieEnable,
      cookieTTL: apiClient.cookieTTL,
      clientCallback: apiClient.clientCallback
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiClient = this.createFromForm();
    if (apiClient.id !== undefined) {
      this.subscribeToSaveResponse(this.apiClientService.update(apiClient));
    } else {
      this.subscribeToSaveResponse(this.apiClientService.create(apiClient));
    }
  }

  private createFromForm(): IApiClient {
    return {
      ...new ApiClient(),
      id: this.editForm.get(['id']).value,
      clientId: this.editForm.get(['clientId']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      cookieEnable: this.editForm.get(['cookieEnable']).value,
      cookieTTL: this.editForm.get(['cookieTTL']).value,
      clientCallback: this.editForm.get(['clientCallback']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiClient>>) {
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
