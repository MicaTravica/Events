export class Place {
    public id: number;
    public name: string;
    public address: string;
    public latitude: number;
    public longitude: number;
    public halls: any[];


    constructor(id?: number, name?: string, address?: string, latitude?: number, longitude?: number, halls?: any[]) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.halls = halls;
    }

}
