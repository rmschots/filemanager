import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ManagedFile } from '../models/managed-file';

@Injectable()
export class FileService {

  constructor(private _http: HttpClient) {
  }

  findAllFilesInDirectory(directoryStructure: string[]): Observable<ManagedFile[]> {
    const pathVariable = directoryStructure.map(dir => encodeURIComponent(dir)).join(',');
    return this._http.get<ManagedFile[]>('/api/files/' + pathVariable)
      .map((data: ManagedFile[]) => data.map(entry => ManagedFile.fromJSON(entry)));
  }


}
