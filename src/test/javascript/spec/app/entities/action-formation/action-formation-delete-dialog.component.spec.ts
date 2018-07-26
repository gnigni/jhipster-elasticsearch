/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ActionFormationDeleteDialogComponent } from 'app/entities/action-formation/action-formation-delete-dialog.component';
import { ActionFormationService } from 'app/entities/action-formation/action-formation.service';

describe('Component Tests', () => {
    describe('ActionFormation Management Delete Component', () => {
        let comp: ActionFormationDeleteDialogComponent;
        let fixture: ComponentFixture<ActionFormationDeleteDialogComponent>;
        let service: ActionFormationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ActionFormationDeleteDialogComponent]
            })
                .overrideTemplate(ActionFormationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActionFormationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActionFormationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
