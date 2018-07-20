import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElasticQuery } from 'app/shared/model/elastic-query.model';

@Component({
    selector: 'jhi-elastic-query-detail',
    templateUrl: './elastic-query-detail.component.html'
})
export class ElasticQueryDetailComponent implements OnInit {
    elasticQuery: IElasticQuery;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elasticQuery }) => {
            this.elasticQuery = elasticQuery;
        });
    }

    previousState() {
        window.history.back();
    }
}
