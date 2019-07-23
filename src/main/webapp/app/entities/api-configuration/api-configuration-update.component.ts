import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiConfiguration, ApiConfiguration } from 'app/shared/model/api-configuration.model';
import { ApiConfigurationService } from './api-configuration.service';

@Component({
  selector: 'jhi-api-configuration-update',
  templateUrl: './api-configuration-update.component.html'
})
export class ApiConfigurationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
    value: []
  });

  constructor(
    protected apiConfigurationService: ApiConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiConfiguration }) => {
      this.updateForm(apiConfiguration);
    });
  }

  updateForm(apiConfiguration: IApiConfiguration) {
    this.editForm.patchValue({
      id: apiConfiguration.id,
      code: apiConfiguration.code,
      description: apiConfiguration.description,
      value: apiConfiguration.value
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiConfiguration = this.createFromForm();
    if (apiConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.apiConfigurationService.update(apiConfiguration));
    } else {
      this.subscribeToSaveResponse(this.apiConfigurationService.create(apiConfiguration));
    }
  }

  private createFromForm(): IApiConfiguration {
    return {
      ...new ApiConfiguration(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      description: this.editForm.get(['description']).value,
      value: this.editForm.get(['value']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiConfiguration>>) {
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
