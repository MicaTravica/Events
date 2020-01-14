import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'addressFormat'
})
export class AddressFormatPipe implements PipeTransform {

  transform(value: any): string {
    return value.name + ', ' + value.address;
  }

}
