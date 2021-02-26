import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPagina, Pagina } from 'app/shared/model/pagina.model';
import { PaginaService } from './pagina.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-pagina-update',
  templateUrl: './pagina-update.component.html',
})
export class PaginaUpdateComponent implements OnInit {
  isSaving = false;
  fechaPublicacionDp: any;
  fechaExpiracionDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [null, [Validators.required]],
    subTitulo: [],
    descripcion: [null, [Validators.required]],
    orden: [null, [Validators.required]],
    fechaPublicacion: [],
    fechaExpiracion: [],
    activa: [null, [Validators.required]],
    etiqueta: [null, [Validators.required]],
    images: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected paginaService: PaginaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pagina }) => {
      this.updateForm(pagina);
    });
  }

  updateForm(pagina: IPagina): void {
    this.editForm.patchValue({
      id: pagina.id,
      titulo: pagina.titulo,
      subTitulo: pagina.subTitulo,
      descripcion: pagina.descripcion,
      orden: pagina.orden,
      fechaPublicacion: pagina.fechaPublicacion,
      fechaExpiracion: pagina.fechaExpiracion,
      activa: pagina.activa,
      etiqueta: pagina.etiqueta,
      images: pagina.images,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('backendApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pagina = this.createFromForm();
    if (pagina.id !== undefined) {
      this.subscribeToSaveResponse(this.paginaService.update(pagina));
    } else {
      this.subscribeToSaveResponse(this.paginaService.create(pagina));
    }
  }

  private createFromForm(): IPagina {
    return {
      ...new Pagina(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      subTitulo: this.editForm.get(['subTitulo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      orden: this.editForm.get(['orden'])!.value,
      fechaPublicacion: this.editForm.get(['fechaPublicacion'])!.value,
      fechaExpiracion: this.editForm.get(['fechaExpiracion'])!.value,
      activa: this.editForm.get(['activa'])!.value,
      etiqueta: this.editForm.get(['etiqueta'])!.value,
      images: this.editForm.get(['images'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPagina>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
