import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IElasticQuery } from 'app/shared/model/elastic-query.model';

type EntityResponseType = HttpResponse<IElasticQuery>;
type EntityArrayResponseType = HttpResponse<IElasticQuery[]>;

@Injectable({ providedIn: 'root' })
export class ElasticQueryService {
    private resourceUrl = SERVER_API_URL + 'api/elastic-queries';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/elastic-queries';

    constructor(private http: HttpClient) {}

    create(elasticQuery: IElasticQuery): Observable<EntityResponseType> {
        return this.http.post<IElasticQuery>(this.resourceUrl, elasticQuery, { observe: 'response' });
    }

    update(elasticQuery: IElasticQuery): Observable<EntityResponseType> {
        return this.http.put<IElasticQuery>(this.resourceUrl, elasticQuery, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IElasticQuery>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IElasticQuery[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IElasticQuery[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
