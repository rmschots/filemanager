import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FileService } from '../../restservices/file.service';

@Component({
  selector: 'fm-download-confirm',
  templateUrl: './download-confirm.component.html',
  styleUrls: ['./download-confirm.component.scss']
})
export class DownloadConfirmComponent {

  constructor(private _dialogRef: MatDialogRef<DownloadConfirmComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _fileService: FileService) {
  }

  download() {
    this._fileService.downloadFile(this.data.path, this.data.file.filename);
  }

}
