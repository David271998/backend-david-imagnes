import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared/shared.module';
import { PaginaComponent } from './pagina.component';
import { PaginaDetailComponent } from './pagina-detail.component';
import { PaginaUpdateComponent } from './pagina-update.component';
import { PaginaDeleteDialogComponent } from './pagina-delete-dialog.component';
import { paginaRoute } from './pagina.route';

@NgModule({
  imports: [BackendSharedModule, RouterModule.forChild(paginaRoute)],
  declarations: [PaginaComponent, PaginaDetailComponent, PaginaUpdateComponent, PaginaDeleteDialogComponent],
  entryComponents: [PaginaDeleteDialogComponent],
})
export class BackendPaginaModule {}
