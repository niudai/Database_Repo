import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { JExceptionDetailComponent } from 'app/entities/j-exception/j-exception-detail.component';
import { JException } from 'app/shared/model/j-exception.model';

describe('Component Tests', () => {
  describe('JException Management Detail Component', () => {
    let comp: JExceptionDetailComponent;
    let fixture: ComponentFixture<JExceptionDetailComponent>;
    const route = ({ data: of({ jException: new JException(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [JExceptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JExceptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JExceptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jException on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jException).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
