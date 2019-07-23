/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ApiCredentialService } from 'app/entities/api-credential/api-credential.service';
import { IApiCredential, ApiCredential } from 'app/shared/model/api-credential.model';

describe('Service Tests', () => {
  describe('ApiCredential Service', () => {
    let injector: TestBed;
    let service: ApiCredentialService;
    let httpMock: HttpTestingController;
    let elemDefault: IApiCredential;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ApiCredentialService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ApiCredential(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ApiCredential', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new ApiCredential(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ApiCredential', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            idpName: 'BBBBBB',
            idpDscription: 'BBBBBB',
            entityId: 'BBBBBB',
            clientId: 'BBBBBB',
            clientSecret: 'BBBBBB',
            issuer: 'BBBBBB',
            scope: 'BBBBBB',
            callbackUrl: 'BBBBBB',
            certCacheTTL: 1,
            tokenTTTL: 1,
            idpUrl: 'BBBBBB',
            idpValidateUrl: 'BBBBBB',
            idpUserInfoUrl: 'BBBBBB',
            idpLogoutUrl: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ApiCredential', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            idpName: 'BBBBBB',
            idpDscription: 'BBBBBB',
            entityId: 'BBBBBB',
            clientId: 'BBBBBB',
            clientSecret: 'BBBBBB',
            issuer: 'BBBBBB',
            scope: 'BBBBBB',
            callbackUrl: 'BBBBBB',
            certCacheTTL: 1,
            tokenTTTL: 1,
            idpUrl: 'BBBBBB',
            idpValidateUrl: 'BBBBBB',
            idpUserInfoUrl: 'BBBBBB',
            idpLogoutUrl: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ApiCredential', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
