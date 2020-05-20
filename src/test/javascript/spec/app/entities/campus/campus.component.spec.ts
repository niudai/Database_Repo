import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { CampusComponent } from 'app/entities/campus/campus.component';
import { CampusService } from 'app/entities/campus/campus.service';
import { Campus } from 'app/shared/model/campus.model';

describe('Component Tests', () => {
  describe('Campus Management Component', () => {
    let comp: CampusComponent;
    let fixture: ComponentFixture<CampusComponent>;
    let service: CampusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [CampusComponent],
      })
        .overrideTemplate(CampusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Campus(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.campuses && comp.campuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
