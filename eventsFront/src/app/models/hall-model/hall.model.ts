import { Place } from '../place-model/place.model';
import { Sector } from '../sector-model/sector.model';
import { EventEntity } from '../event-model/event.model';

export class Hall {
    public id: number;
    public name: string;
    public place: Place;
    public sectors: Sector[];
    public events: EventEntity[];

    constructor(id?: number, name?: string, place?: Place, sectors?: Sector[]) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.sectors = sectors;
    }


}
