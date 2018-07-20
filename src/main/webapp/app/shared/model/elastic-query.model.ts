export interface IElasticQuery {
    id?: number;
    quoi?: string;
    ou?: string;
}

export class ElasticQuery implements IElasticQuery {
    constructor(public id?: number, public quoi?: string, public ou?: string) {}
}
