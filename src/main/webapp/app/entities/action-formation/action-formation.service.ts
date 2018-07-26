import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActionFormation } from 'app/shared/model/action-formation.model';

type EntityResponseType = HttpResponse<IActionFormation>;
type EntityArrayResponseType = HttpResponse<IActionFormation[]>;

@Injectable({ providedIn: 'root' })
export class ActionFormationService {
    private resourceUrl = SERVER_API_URL + 'api/action-formations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/action-formations';

    constructor(private http: HttpClient) {}

    create(actionFormation: IActionFormation): Observable<EntityResponseType> {
        return this.http.post<IActionFormation>(this.resourceUrl, actionFormation, { observe: 'response' });
    }

    update(actionFormation: IActionFormation): Observable<EntityResponseType> {
        return this.http.put<IActionFormation>(this.resourceUrl, actionFormation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActionFormation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActionFormation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActionFormation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
