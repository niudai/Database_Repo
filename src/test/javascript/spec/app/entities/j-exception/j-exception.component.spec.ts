import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { JExceptionComponent } from 'app/entities/j-exception/j-exception.component';
import { JExceptionService } from 'app/entities/j-exception/j-exception.service';
import { JException } from 'app/shared/model/j-exception.model';

describe('Component Tests', () => {
  describe('JException Management Component', () => {
    let comp: JExceptionComponent;
    let fixture: ComponentFixture<JExceptionComponent>;
    let service: JExceptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [JExceptionComponent]
      })
        .overrideTemplate(JExceptionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JExceptionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JExceptionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JException(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jExceptions && comp.jExceptions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
