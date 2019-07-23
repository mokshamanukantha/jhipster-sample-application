import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IApiSession, ApiSession } from 'app/shared/model/api-session.model';
import { ApiSessionService } from './api-session.service';

@Component({
  selector: 'jhi-api-session-update',
  templateUrl: './api-session-update.component.html'
})
export class ApiSessionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    sessionId: [],
    token: []
  });

  constructor(protected apiSessionService: ApiSessionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ apiSession }) => {
      this.updateForm(apiSession);
    });
  }

  updateForm(apiSession: IApiSession) {
    this.editForm.patchValue({
      id: apiSession.id,
      sessionId: apiSession.sessionId,
      token: apiSession.token
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const apiSession = this.createFromForm();
    if (apiSession.id !== undefined) {
      this.subscribeToSaveResponse(this.apiSessionService.update(apiSession));
    } else {
      this.subscribeToSaveResponse(this.apiSessionService.create(apiSession));
    }
  }

  private createFromForm(): IApiSession {
    return {
      ...new ApiSession(),
      id: this.editForm.get(['id']).value,
      sessionId: this.editForm.get(['sessionId']).value,
      token: this.editForm.get(['token']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiSession>>) {
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
