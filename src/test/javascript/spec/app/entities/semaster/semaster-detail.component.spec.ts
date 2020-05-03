import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { SemasterDetailComponent } from 'app/entities/semaster/semaster-detail.component';
import { Semaster } from 'app/shared/model/semaster.model';

describe('Component Tests', () => {
  describe('Semaster Management Detail Component', () => {
    let comp: SemasterDetailComponent;
    let fixture: ComponentFixture<SemasterDetailComponent>;
    const route = ({ data: of({ semaster: new Semaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SemasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SemasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SemasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load semaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.semaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
