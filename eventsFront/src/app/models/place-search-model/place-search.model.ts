export class PlaceSearch {
    public numOfPage: number;
    public sizeOfPage: number;
    public sortBy: string;
    public ascending: boolean;
    public name: string;
    public address: string;


    constructor(numOfPage?: number, sizeOfPage?: number, sortBy?: string, ascending?: boolean, name?: string, address?: string) {
        this.numOfPage = numOfPage;
        this.sizeOfPage = sizeOfPage;
        this.sortBy = sortBy;
        this.ascending = ascending;
        this.name = name;
        this.address = address;
    }
}
