export class User {
  fullname: string;
  email: string;
  picture: string;

  static fromJSON(json: User): User {
    const result = Object.create(User);
    return Object.assign(result, json);
  }
}
