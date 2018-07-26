/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ActionFormationDetailComponent } from 'app/entities/action-formation/action-formation-detail.component';
import { ActionFormation } from 'app/shared/model/action-formation.model';

describe('Component Tests', () => {
    describe('ActionFormation Management Detail Component', () => {
        let comp: ActionFormationDetailComponent;
        let fixture: ComponentFixture<ActionFormationDetailComponent>;
        const route = ({ data: of({ actionFormation: new ActionFormation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ActionFormationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ActionFormationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActionFormationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.actionFormation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
