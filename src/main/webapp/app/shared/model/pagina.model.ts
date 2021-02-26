import { Moment } from 'moment';

export interface IPagina {
  id?: number;
  titulo?: string;
  subTitulo?: string;
  descripcion?: any;
  orden?: number;
  fechaPublicacion?: Moment;
  fechaExpiracion?: Moment;
  activa?: boolean;
  etiqueta?: string;
  images?: string;
}

export class Pagina implements IPagina {
  constructor(
    public id?: number,
    public titulo?: string,
    public subTitulo?: string,
    public descripcion?: any,
    public orden?: number,
    public fechaPublicacion?: Moment,
    public fechaExpiracion?: Moment,
    public activa?: boolean,
    public etiqueta?: string,
    public images?: string
  ) {
    this.activa = this.activa || false;
  }
}
