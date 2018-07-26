/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ActionFormationUpdateComponent } from 'app/entities/action-formation/action-formation-update.component';
import { ActionFormationService } from 'app/entities/action-formation/action-formation.service';
import { ActionFormation } from 'app/shared/model/action-formation.model';

describe('Component Tests', () => {
    describe('ActionFormation Management Update Component', () => {
        let comp: ActionFormationUpdateComponent;
        let fixture: ComponentFixture<ActionFormationUpdateComponent>;
        let service: ActionFormationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ActionFormationUpdateComponent]
            })
                .overrideTemplate(ActionFormationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActionFormationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionFormationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActionFormation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actionFormation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActionFormation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.actionFormation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
