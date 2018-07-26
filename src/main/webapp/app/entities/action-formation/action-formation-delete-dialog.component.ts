import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionFormation } from 'app/shared/model/action-formation.model';
import { ActionFormationService } from './action-formation.service';

@Component({
    selector: 'jhi-action-formation-delete-dialog',
    templateUrl: './action-formation-delete-dialog.component.html'
})
export class ActionFormationDeleteDialogComponent {
    actionFormation: IActionFormation;

    constructor(
        private actionFormationService: ActionFormationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.actionFormationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'actionFormationListModification',
                content: 'Deleted an actionFormation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-action-formation-delete-popup',
    template: ''
})
export class ActionFormationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ actionFormation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActionFormationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.actionFormation = actionFormation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
