import {Deserializable} from '../deserializable.model';

export class User implements Deserializable {
  public name: string;
  public surname: string;
  public email: string;
  public phone: string;
  public username: string;
  public password: string;

  deserialize(input: any): this {
    return Object.assign(this, input);
  }
  
  getEmail() {
    return this.email;
  }
}