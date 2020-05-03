import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { ExceptionComponent } from 'app/entities/exception/exception.component';
import { ExceptionService } from 'app/entities/exception/exception.service';
import { Exception } from 'app/shared/model/exception.model';

describe('Component Tests', () => {
  describe('Exception Management Component', () => {
    let comp: ExceptionComponent;
    let fixture: ComponentFixture<ExceptionComponent>;
    let service: ExceptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExceptionComponent]
      })
        .overrideTemplate(ExceptionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExceptionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExceptionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Exception(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.exceptions && comp.exceptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
