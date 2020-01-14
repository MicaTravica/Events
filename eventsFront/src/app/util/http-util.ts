import { HttpHeaders } from '@angular/common/http';

export const httpOptions = {
    headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json'
    })
};

export const authHttpOptions = (token: string) => {
    return {
        headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + token
        })
    };
};
