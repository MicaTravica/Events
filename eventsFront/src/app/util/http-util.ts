import { HttpHeaders } from '@angular/common/http';

export const httpOptions = (): HttpHeaders => {
    return new HttpHeaders()
        .append('Content-Type', 'application/json')
        .append('Accept', 'application/json');
};

export const authHttpOptions = (token: string): HttpHeaders => {
    return new HttpHeaders()
        .append('Content-Type', 'application/json')
        .append('Accept', 'application/json')
        .append('Authorization', 'Bearer ' + token);
};
