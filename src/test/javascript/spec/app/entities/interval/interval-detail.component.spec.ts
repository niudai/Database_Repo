import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { IntervalDetailComponent } from 'app/entities/interval/interval-detail.component';
import { Interval } from 'app/shared/model/interval.model';

describe('Component Tests', () => {
  describe('Interval Management Detail Component', () => {
    let comp: IntervalDetailComponent;
    let fixture: ComponentFixture<IntervalDetailComponent>;
    const route = ({ data: of({ interval: new Interval(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [IntervalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IntervalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntervalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load interval on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.interval).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
