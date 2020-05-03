import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { SchoolClassDetailComponent } from 'app/entities/school-class/school-class-detail.component';
import { SchoolClass } from 'app/shared/model/school-class.model';

describe('Component Tests', () => {
  describe('SchoolClass Management Detail Component', () => {
    let comp: SchoolClassDetailComponent;
    let fixture: ComponentFixture<SchoolClassDetailComponent>;
    const route = ({ data: of({ schoolClass: new SchoolClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SchoolClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SchoolClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchoolClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schoolClass on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schoolClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
