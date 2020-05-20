import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { CampusDetailComponent } from 'app/entities/campus/campus-detail.component';
import { Campus } from 'app/shared/model/campus.model';

describe('Component Tests', () => {
  describe('Campus Management Detail Component', () => {
    let comp: CampusDetailComponent;
    let fixture: ComponentFixture<CampusDetailComponent>;
    const route = ({ data: of({ campus: new Campus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [CampusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CampusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CampusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load campus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.campus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
