import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { SchoolClassComponent } from 'app/entities/school-class/school-class.component';
import { SchoolClassService } from 'app/entities/school-class/school-class.service';
import { SchoolClass } from 'app/shared/model/school-class.model';

describe('Component Tests', () => {
  describe('SchoolClass Management Component', () => {
    let comp: SchoolClassComponent;
    let fixture: ComponentFixture<SchoolClassComponent>;
    let service: SchoolClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SchoolClassComponent]
      })
        .overrideTemplate(SchoolClassComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchoolClassComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchoolClassService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SchoolClass(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.schoolClasses && comp.schoolClasses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
