import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiCredential, ApiCredential } from 'app/shared/model/api-credential.model';
import { ApiCredentialService } from './api-credential.service';

@Component({
  selector: 'jhi-api-credential-update',
  templateUrl: './api-credential-update.component.html'
})
export class ApiCredentialUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.required]],
    idpName: [null, [Validators.required]],
    idpDscription: [null, [Validators.required]],
    entityId: [null, [Validators.required]],
    clientId: [null, [Validators.required]],
    clientSecret: [null, [Validators.required]],
    issuer: [null, [Validators.required]],
    scope: [null, [Validators.required]],
    callbackUrl: [null, [Validators.required]],
    certCacheTTL: [null, [Validators.required]],
    tokenTTTL: [null, [Validators.required]],
    idpUrl: [null, [Validators.required]],
    idpValidateUrl: [null, [Validators.required]],
    idpUserInfoUrl: [null, [Validators.required]],
    idpLogoutUrl: [null, [Validators.required]]
  });

  constructor(protected apiCredentialService: ApiCredentialService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiCredential }) => {
      this.updateForm(apiCredential);
    });
  }

  updateForm(apiCredential: IApiCredential) {
    this.editForm.patchValue({
      id: apiCredential.id,
      name: apiCredential.name,
      description: apiCredential.description,
      idpName: apiCredential.idpName,
      idpDscription: apiCredential.idpDscription,
      entityId: apiCredential.entityId,
      clientId: apiCredential.clientId,
      clientSecret: apiCredential.clientSecret,
      issuer: apiCredential.issuer,
      scope: apiCredential.scope,
      callbackUrl: apiCredential.callbackUrl,
      certCacheTTL: apiCredential.certCacheTTL,
      tokenTTTL: apiCredential.tokenTTTL,
      idpUrl: apiCredential.idpUrl,
      idpValidateUrl: apiCredential.idpValidateUrl,
      idpUserInfoUrl: apiCredential.idpUserInfoUrl,
      idpLogoutUrl: apiCredential.idpLogoutUrl
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiCredential = this.createFromForm();
    if (apiCredential.id !== undefined) {
      this.subscribeToSaveResponse(this.apiCredentialService.update(apiCredential));
    } else {
      this.subscribeToSaveResponse(this.apiCredentialService.create(apiCredential));
    }
  }

  private createFromForm(): IApiCredential {
    return {
      ...new ApiCredential(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      idpName: this.editForm.get(['idpName']).value,
      idpDscription: this.editForm.get(['idpDscription']).value,
      entityId: this.editForm.get(['entityId']).value,
      clientId: this.editForm.get(['clientId']).value,
      clientSecret: this.editForm.get(['clientSecret']).value,
      issuer: this.editForm.get(['issuer']).value,
      scope: this.editForm.get(['scope']).value,
      callbackUrl: this.editForm.get(['callbackUrl']).value,
      certCacheTTL: this.editForm.get(['certCacheTTL']).value,
      tokenTTTL: this.editForm.get(['tokenTTTL']).value,
      idpUrl: this.editForm.get(['idpUrl']).value,
      idpValidateUrl: this.editForm.get(['idpValidateUrl']).value,
      idpUserInfoUrl: this.editForm.get(['idpUserInfoUrl']).value,
      idpLogoutUrl: this.editForm.get(['idpLogoutUrl']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiCredential>>) {
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
