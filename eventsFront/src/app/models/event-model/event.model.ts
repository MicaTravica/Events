import { EventState } from './event-state.enum';
import { EventType } from './event-type.enum';
import { Hall } from '../hall-model/hall.model';
import { PriceList } from '../price-list-model/price-list.model';

export class EventEntity {
    public id: number;
    public name: string;
    public description: string;
    public fromDate: Date;
    public toDate: Date;
    public eventState: EventState;
    public eventType: EventType;
    public halls: Hall[];
    public priceList: PriceList[];
    public mediaList: any[]; // media list

    constructor(id?: number, name?: string, description?: string, fromDate?: Date, toDate?: Date, eventState?: EventState,
                eventType?: EventType, halls?: Hall[], priceList?: PriceList[], mediaList?: any[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.eventState = eventState;
        this.eventType = eventType;
        this.halls = halls;
        this.priceList = priceList;
        this.mediaList = mediaList;
    }
}
