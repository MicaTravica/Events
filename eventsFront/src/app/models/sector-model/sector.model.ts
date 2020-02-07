import { Hall } from '../hall-model/hall.model';

export class Sector {
    public id: number;
    public name: string;
    public sectorRows: number;
    public sectorColumns: number;
    public sectorCapacity: number;
    public hallID: number;


    constructor(id: number, name: string, sectorRows: number, sectorColumns: number, sectorCapacity: number,  hallID: number) {
        this.id = id;
        this.name = name;
        this.sectorRows = sectorRows;
        this.sectorColumns = sectorColumns;
        this.sectorCapacity = sectorCapacity;
        this.hallID = hallID;

    }

}
