import { EventEntity } from '../event-model/event.model';

export class Ticket {
    public id: number;
    public barCode: string;
    public ticketState: string;
    public userId: number;
    public version: number;
    public sectorName: string;
    public hallName: string;
    public seatRow: number;
    public seatColumn: number;
    public payPalPaymentID: string;
    public payPalToken: string;
    public payPalPayerID: string;
    public event: EventEntity;

    public Ticket(  id: number, barCode: string,
                    ticketState: string, userId: number,
                    version: number, sectorName: string,
                    hallName: string, seatRow: number, seatColumn: number,
                    event: EventEntity) {
        this.id = id;
        this.barCode = barCode;
        this.ticketState = ticketState;
        this.userId = userId;
        this.version = version;
        this.sectorName = sectorName;
        this.hallName = hallName;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.event = event;
    }
}
