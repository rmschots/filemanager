export class ManagedFile {
  static fromJSON(json: ManagedFile): ManagedFile {
    const result = Object.create(ManagedFile);
    return Object.assign(result, json, {
      created: new Date(json.created),
      modified: new Date(json.modified)
    });
  }

  constructor(public filename: string,
              public created: Date,
              public modified: Date,
              public size: number) {
  }
}
