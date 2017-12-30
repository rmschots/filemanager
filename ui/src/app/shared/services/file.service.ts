import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ManagedFile } from '../models/managed-file';

@Injectable()
export class FileService {

  constructor(private _http: HttpClient) {
  }

  findAllFilesInDirectory(directoryStructure: string[]): Observable<ManagedFile[]> {
    const pathVariable = directoryStructure.map(dir => encodeURIComponent(dir)).join(',');
    return this._http.get<ManagedFile[]>(`/api/files/${pathVariable}`)
      .map((data: ManagedFile[]) => data.map(entry => ManagedFile.fromJSON(entry)));
  }

  downloadFile(directoryStructure: string[], filename: string) {
    const pathVariable = directoryStructure.map(dir => encodeURIComponent(dir)).join(',');
    window.location.href = `/api/files/download/${pathVariable}/${encodeURIComponent(filename)}`;
  }

  uploadFile(directoryStructure: string[], file: File): Observable<any> {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    const pathVariable = directoryStructure.map(dir => encodeURIComponent(dir)).join(',');
    return this._http.post(`/api/files/upload/${pathVariable}`, formdata, {
      reportProgress: true,
      observe: 'events',
      responseType: 'text'
    });
  }

}
