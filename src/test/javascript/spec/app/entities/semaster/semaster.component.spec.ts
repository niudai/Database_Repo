import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { SemasterComponent } from 'app/entities/semaster/semaster.component';
import { SemasterService } from 'app/entities/semaster/semaster.service';
import { Semaster } from 'app/shared/model/semaster.model';

describe('Component Tests', () => {
  describe('Semaster Management Component', () => {
    let comp: SemasterComponent;
    let fixture: ComponentFixture<SemasterComponent>;
    let service: SemasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SemasterComponent]
      })
        .overrideTemplate(SemasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SemasterComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SemasterService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Semaster(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.semasters && comp.semasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
