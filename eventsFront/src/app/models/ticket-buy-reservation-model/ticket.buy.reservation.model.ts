export class TicketBuyReservation {
    public ticketIDs: number[];
    public userId: number;
    public payPalPaymentID: string;
    public payPalToken: string;
    public payPalPayerID: string;

    TicketBuyReservation(
        userId: number, ticketIDs: number[]) {
        this.userId = userId;
        this.ticketIDs = ticketIDs;
    }
}
