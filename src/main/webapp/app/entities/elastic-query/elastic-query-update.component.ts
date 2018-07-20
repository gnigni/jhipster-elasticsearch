import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IElasticQuery } from 'app/shared/model/elastic-query.model';
import { ElasticQueryService } from './elastic-query.service';

@Component({
    selector: 'jhi-elastic-query-update',
    templateUrl: './elastic-query-update.component.html'
})
export class ElasticQueryUpdateComponent implements OnInit {
    private _elasticQuery: IElasticQuery;
    isSaving: boolean;

    constructor(private elasticQueryService: ElasticQueryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ elasticQuery }) => {
            this.elasticQuery = elasticQuery;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.elasticQuery.id !== undefined) {
            this.subscribeToSaveResponse(this.elasticQueryService.update(this.elasticQuery));
        } else {
            this.subscribeToSaveResponse(this.elasticQueryService.create(this.elasticQuery));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IElasticQuery>>) {
        result.subscribe((res: HttpResponse<IElasticQuery>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get elasticQuery() {
        return this._elasticQuery;
    }

    set elasticQuery(elasticQuery: IElasticQuery) {
        this._elasticQuery = elasticQuery;
    }
}
