import { EventState } from '../event-model/event-state.enum';
import { EventType } from '../event-model/event-type.enum';

export class EventSearch {
    public numOfPage: number;
    public sizeOfPage: number;
    public sortBy: string;
    public ascending: boolean;
    public name: string;
    public fromDate: Date;
    public toDate: Date;
    public eventState: EventState;
    public eventType: EventType;
    public placeId: number;

    constructor(numOfPage?: number, sizeOfPage?: number, sortBy?: string, ascending?: boolean, name?: string, fromDate?: Date,
                toDate?: Date, eventState?: EventState, eventType?: EventType, placeId?: number) {
        this.numOfPage = numOfPage;
        this.sizeOfPage = sizeOfPage;
        this.sortBy = sortBy;
        this.ascending = ascending;
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.eventState = eventState;
        this.eventType = eventType;
        this.placeId = placeId;
    }
}
