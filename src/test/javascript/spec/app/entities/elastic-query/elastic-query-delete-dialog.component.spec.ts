/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterElasticsearchTestModule } from '../../../test.module';
import { ElasticQueryDeleteDialogComponent } from 'app/entities/elastic-query/elastic-query-delete-dialog.component';
import { ElasticQueryService } from 'app/entities/elastic-query/elastic-query.service';

describe('Component Tests', () => {
    describe('ElasticQuery Management Delete Component', () => {
        let comp: ElasticQueryDeleteDialogComponent;
        let fixture: ComponentFixture<ElasticQueryDeleteDialogComponent>;
        let service: ElasticQueryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterElasticsearchTestModule],
                declarations: [ElasticQueryDeleteDialogComponent]
            })
                .overrideTemplate(ElasticQueryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ElasticQueryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElasticQueryService);
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
