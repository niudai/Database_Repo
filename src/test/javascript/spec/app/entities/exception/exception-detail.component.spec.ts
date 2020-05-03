import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { ExceptionDetailComponent } from 'app/entities/exception/exception-detail.component';
import { Exception } from 'app/shared/model/exception.model';

describe('Component Tests', () => {
  describe('Exception Management Detail Component', () => {
    let comp: ExceptionDetailComponent;
    let fixture: ComponentFixture<ExceptionDetailComponent>;
    const route = ({ data: of({ exception: new Exception(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExceptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExceptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExceptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load exception on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.exception).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
