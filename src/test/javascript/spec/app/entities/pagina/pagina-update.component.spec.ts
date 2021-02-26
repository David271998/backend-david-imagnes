import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { PaginaUpdateComponent } from 'app/entities/pagina/pagina-update.component';
import { PaginaService } from 'app/entities/pagina/pagina.service';
import { Pagina } from 'app/shared/model/pagina.model';

describe('Component Tests', () => {
  describe('Pagina Management Update Component', () => {
    let comp: PaginaUpdateComponent;
    let fixture: ComponentFixture<PaginaUpdateComponent>;
    let service: PaginaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [PaginaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaginaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaginaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaginaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pagina(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pagina();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
