import { Place } from '../place-model/place.model';

export class Hall {
    public id: number;
    public name: string;
    public place: Place;
    public sectors: any[];
    public events: any[];

    constructor(id: number, name: string, place: Place, sectors: any){
        this.id = id;
        this.name = name;
        this.place = place;
        this.sectors = sectors;
    }


}
