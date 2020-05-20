import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { IntervalComponent } from 'app/entities/interval/interval.component';
import { IntervalService } from 'app/entities/interval/interval.service';
import { Interval } from 'app/shared/model/interval.model';

describe('Component Tests', () => {
  describe('Interval Management Component', () => {
    let comp: IntervalComponent;
    let fixture: ComponentFixture<IntervalComponent>;
    let service: IntervalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [IntervalComponent],
      })
        .overrideTemplate(IntervalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntervalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntervalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Interval(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.intervals && comp.intervals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
