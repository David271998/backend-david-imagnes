import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPagina } from 'app/shared/model/pagina.model';
import { PaginaService } from './pagina.service';
import { PaginaDeleteDialogComponent } from './pagina-delete-dialog.component';

@Component({
  selector: 'jhi-pagina',
  templateUrl: './pagina.component.html',
})
export class PaginaComponent implements OnInit, OnDestroy {
  paginas?: IPagina[];
  eventSubscriber?: Subscription;

  constructor(
    protected paginaService: PaginaService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paginaService.query().subscribe((res: HttpResponse<IPagina[]>) => (this.paginas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaginas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPagina): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPaginas(): void {
    this.eventSubscriber = this.eventManager.subscribe('paginaListModification', () => this.loadAll());
  }

  delete(pagina: IPagina): void {
    const modalRef = this.modalService.open(PaginaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pagina = pagina;
  }
}
