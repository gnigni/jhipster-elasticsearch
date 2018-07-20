import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElasticQuery } from 'app/shared/model/elastic-query.model';
import { ElasticQueryService } from './elastic-query.service';

@Component({
    selector: 'jhi-elastic-query-delete-dialog',
    templateUrl: './elastic-query-delete-dialog.component.html'
})
export class ElasticQueryDeleteDialogComponent {
    elasticQuery: IElasticQuery;

    constructor(
        private elasticQueryService: ElasticQueryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.elasticQueryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'elasticQueryListModification',
                content: 'Deleted an elasticQuery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-elastic-query-delete-popup',
    template: ''
})
export class ElasticQueryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elasticQuery }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ElasticQueryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.elasticQuery = elasticQuery;
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
