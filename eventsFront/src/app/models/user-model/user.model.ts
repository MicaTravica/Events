import {Deserializable} from '../deserializable.model';

export class User implements Deserializable {
  public id: number;
  public name: string;
  public surname: string;
  public email: string;
  public phone: string;
  public username: string;
  public password: string;

  constructor(id: number, name: string, surname: string, email: string, phone: string, username: string) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phone = phone;
    this.username = username;
  }

  deserialize(input: any): this {
    return Object.assign(this, input);
  }

  getEmail() {
    return this.email;
  }
}
