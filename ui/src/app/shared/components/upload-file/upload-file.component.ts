import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FileService } from '../../restservices/file.service';
import { DownloadConfirmComponent } from '../download-confirm/download-confirm.component';
import { Unsubscribable } from '../../util/unsubscribable';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent extends Unsubscribable {

  canUpload$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  isUploading$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  uploadedAmount$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  totalAmount$: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  private selectedFiles: FileList;

  constructor(private _dialogRef: MatDialogRef<DownloadConfirmComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _fileService: FileService) {
    super();
  }

  selectFile(event: any) {
    console.log('file selected', event);
    this.selectedFiles = event.target.files;
    this.canUpload$.next(this.selectedFiles.length === 1);
  }

  upload() {
    this.isUploading$.next(true);
    this.canUpload$.next(false);
    this._fileService.uploadFile(this.data.path, this.selectedFiles.item(0))
      .takeUntil(this.ngUnsubscribe$)
      .subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.uploadedAmount$.next(event.loaded);
          this.totalAmount$.next(event.total);
        } else if (event instanceof HttpResponse) {
          this.isUploading$.next(false);
          this._dialogRef.close(true);
        }
      });
  }

  get downloadProgress(): Observable<number> {
    return this.uploadedAmount$
      .combineLatest(this.totalAmount$)
      .map(([current, total]) => current * 100 / total);
  }

}
