import { Hall } from '../hall-model/hall.model';

export class Place {
    public id: number;
    public name: string;
    public address: string;
    public latitude: number;
    public longitude: number;
    public halls: Hall[];


    constructor(id?: number, name?: string, address?: string, latitude?: number, longitude?: number, halls?: Hall[]) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.halls = halls;
    }

}
