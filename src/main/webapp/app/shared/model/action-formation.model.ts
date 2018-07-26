export interface IActionFormation {
    id?: number;
    intituleFormation?: string;
    description?: string;
    nombreHeuresTotal?: number;
}

export class ActionFormation implements IActionFormation {
    constructor(public id?: number, public intituleFormation?: string, public description?: string, public nombreHeuresTotal?: number) {}
}
