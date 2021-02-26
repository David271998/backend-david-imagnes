import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPagina } from 'app/shared/model/pagina.model';

type EntityResponseType = HttpResponse<IPagina>;
type EntityArrayResponseType = HttpResponse<IPagina[]>;

@Injectable({ providedIn: 'root' })
export class PaginaService {
  public resourceUrl = SERVER_API_URL + 'api/paginas';

  constructor(protected http: HttpClient) {}

  create(pagina: IPagina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pagina);
    return this.http
      .post<IPagina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pagina: IPagina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pagina);
    return this.http
      .put<IPagina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPagina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPagina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pagina: IPagina): IPagina {
    const copy: IPagina = Object.assign({}, pagina, {
      fechaPublicacion:
        pagina.fechaPublicacion && pagina.fechaPublicacion.isValid() ? pagina.fechaPublicacion.format(DATE_FORMAT) : undefined,
      fechaExpiracion: pagina.fechaExpiracion && pagina.fechaExpiracion.isValid() ? pagina.fechaExpiracion.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPublicacion = res.body.fechaPublicacion ? moment(res.body.fechaPublicacion) : undefined;
      res.body.fechaExpiracion = res.body.fechaExpiracion ? moment(res.body.fechaExpiracion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pagina: IPagina) => {
        pagina.fechaPublicacion = pagina.fechaPublicacion ? moment(pagina.fechaPublicacion) : undefined;
        pagina.fechaExpiracion = pagina.fechaExpiracion ? moment(pagina.fechaExpiracion) : undefined;
      });
    }
    return res;
  }
}
