import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PaginaService } from 'app/entities/pagina/pagina.service';
import { IPagina, Pagina } from 'app/shared/model/pagina.model';

describe('Service Tests', () => {
  describe('Pagina Service', () => {
    let injector: TestBed;
    let service: PaginaService;
    let httpMock: HttpTestingController;
    let elemDefault: IPagina;
    let expectedResult: IPagina | IPagina[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaginaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pagina(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate, false, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaPublicacion: currentDate.format(DATE_FORMAT),
            fechaExpiracion: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pagina', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaPublicacion: currentDate.format(DATE_FORMAT),
            fechaExpiracion: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPublicacion: currentDate,
            fechaExpiracion: currentDate,
          },
          returnedFromService
        );

        service.create(new Pagina()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pagina', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            subTitulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            orden: 1,
            fechaPublicacion: currentDate.format(DATE_FORMAT),
            fechaExpiracion: currentDate.format(DATE_FORMAT),
            activa: true,
            etiqueta: 'BBBBBB',
            images: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPublicacion: currentDate,
            fechaExpiracion: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pagina', () => {
        const returnedFromService = Object.assign(
          {
            titulo: 'BBBBBB',
            subTitulo: 'BBBBBB',
            descripcion: 'BBBBBB',
            orden: 1,
            fechaPublicacion: currentDate.format(DATE_FORMAT),
            fechaExpiracion: currentDate.format(DATE_FORMAT),
            activa: true,
            etiqueta: 'BBBBBB',
            images: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaPublicacion: currentDate,
            fechaExpiracion: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pagina', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

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
