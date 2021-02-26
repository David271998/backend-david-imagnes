import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPagina, Pagina } from 'app/shared/model/pagina.model';
import { PaginaService } from './pagina.service';
import { PaginaComponent } from './pagina.component';
import { PaginaDetailComponent } from './pagina-detail.component';
import { PaginaUpdateComponent } from './pagina-update.component';

@Injectable({ providedIn: 'root' })
export class PaginaResolve implements Resolve<IPagina> {
  constructor(private service: PaginaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPagina> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pagina: HttpResponse<Pagina>) => {
          if (pagina.body) {
            return of(pagina.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pagina());
  }
}

export const paginaRoute: Routes = [
  {
    path: '',
    component: PaginaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paginas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaginaDetailComponent,
    resolve: {
      pagina: PaginaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paginas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaginaUpdateComponent,
    resolve: {
      pagina: PaginaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paginas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaginaUpdateComponent,
    resolve: {
      pagina: PaginaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Paginas',
    },
    canActivate: [UserRouteAccessService],
  },
];
