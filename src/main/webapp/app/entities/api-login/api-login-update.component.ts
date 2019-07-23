import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IApiLogin, ApiLogin } from 'app/shared/model/api-login.model';
import { ApiLoginService } from './api-login.service';
import { IApiClient } from 'app/shared/model/api-client.model';
import { ApiClientService } from 'app/entities/api-client';
import { IApiCredential } from 'app/shared/model/api-credential.model';
import { ApiCredentialService } from 'app/entities/api-credential';

@Component({
  selector: 'jhi-api-login-update',
  templateUrl: './api-login-update.component.html'
})
export class ApiLoginUpdateComponent implements OnInit {
  isSaving: boolean;

  apiclients: IApiClient[];

  types: IApiCredential[];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    apiClient: [],
    type: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected apiLoginService: ApiLoginService,
    protected apiClientService: ApiClientService,
    protected apiCredentialService: ApiCredentialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiLogin }) => {
      this.updateForm(apiLogin);
    });
    this.apiClientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApiClient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiClient[]>) => response.body)
      )
      .subscribe((res: IApiClient[]) => (this.apiclients = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.apiCredentialService
      .query({ filter: 'apilogin-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IApiCredential[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApiCredential[]>) => response.body)
      )
      .subscribe(
        (res: IApiCredential[]) => {
          if (!this.editForm.get('type').value || !this.editForm.get('type').value.id) {
            this.types = res;
          } else {
            this.apiCredentialService
              .find(this.editForm.get('type').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IApiCredential>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IApiCredential>) => subResponse.body)
              )
              .subscribe(
                (subRes: IApiCredential) => (this.types = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(apiLogin: IApiLogin) {
    this.editForm.patchValue({
      id: apiLogin.id,
      name: apiLogin.name,
      description: apiLogin.description,
      apiClient: apiLogin.apiClient,
      type: apiLogin.type
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiLogin = this.createFromForm();
    if (apiLogin.id !== undefined) {
      this.subscribeToSaveResponse(this.apiLoginService.update(apiLogin));
    } else {
      this.subscribeToSaveResponse(this.apiLoginService.create(apiLogin));
    }
  }

  private createFromForm(): IApiLogin {
    return {
      ...new ApiLogin(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      apiClient: this.editForm.get(['apiClient']).value,
      type: this.editForm.get(['type']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiLogin>>) {
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

  trackApiClientById(index: number, item: IApiClient) {
    return item.id;
  }

  trackApiCredentialById(index: number, item: IApiCredential) {
    return item.id;
  }
}
