import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElasticQuery } from 'app/shared/model/elastic-query.model';
import { Principal } from 'app/core';
import { ElasticQueryService } from './elastic-query.service';

@Component({
    selector: 'jhi-elastic-query',
    templateUrl: './elastic-query.component.html'
})
export class ElasticQueryComponent implements OnInit, OnDestroy {
    elasticQueries: IElasticQuery[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private elasticQueryService: ElasticQueryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.elasticQueryService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IElasticQuery[]>) => (this.elasticQueries = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.elasticQueryService.query().subscribe(
            (res: HttpResponse<IElasticQuery[]>) => {
                this.elasticQueries = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInElasticQueries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IElasticQuery) {
        return item.id;
    }

    registerChangeInElasticQueries() {
        this.eventSubscriber = this.eventManager.subscribe('elasticQueryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
