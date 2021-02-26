import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { PaginaComponent } from 'app/entities/pagina/pagina.component';
import { PaginaService } from 'app/entities/pagina/pagina.service';
import { Pagina } from 'app/shared/model/pagina.model';

describe('Component Tests', () => {
  describe('Pagina Management Component', () => {
    let comp: PaginaComponent;
    let fixture: ComponentFixture<PaginaComponent>;
    let service: PaginaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [PaginaComponent],
      })
        .overrideTemplate(PaginaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaginaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaginaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pagina(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paginas && comp.paginas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
