export class PriceList {

    public id: number;
    public price: number;
    public eventId: number;
    public sectorId: number;


    constructor(id?: number, price?: number, eventId?: number, sectorId?: number) {
        this.id = id;
        this.price = price;
        this.eventId = eventId;
        this.sectorId = sectorId;
    }

}
