import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPagina } from 'app/shared/model/pagina.model';
import { PaginaService } from './pagina.service';

@Component({
  templateUrl: './pagina-delete-dialog.component.html',
})
export class PaginaDeleteDialogComponent {
  pagina?: IPagina;

  constructor(protected paginaService: PaginaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paginaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paginaListModification');
      this.activeModal.close();
    });
  }
}
