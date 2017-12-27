import { Component, OnInit } from '@angular/core';
import { ManagedFile } from '../shared/models/managed-file';
import { ActivatedRoute, Router } from '@angular/router';
import { Unsubscribable } from '../shared/util/unsubscribable';
import { FileService } from '../shared/restservices/file.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-explorer',
  templateUrl: './explorer.component.html',
  styleUrls: ['./explorer.component.scss']
})
export class ExplorerComponent extends Unsubscribable implements OnInit {

  parentDirs: string[] = [];

  files$: BehaviorSubject<ManagedFile[]> = new BehaviorSubject<ManagedFile[]>([]);
  isRefreshing$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private _route: ActivatedRoute,
              private _router: Router,
              private _fileService: FileService) {
    super();
  }

  ngOnInit() {
    console.log('init');
    this._route
      .params
      .takeUntil(this.ngUnsubscribe$)
      .subscribe(params => {
        const dirParams = params['dir'];
        if (dirParams) {
          this.parentDirs = JSON.parse(params['dir']);
        } else {
          this.parentDirs = [];
        }
        this.loadFiles();
      });
  }

  enterDirectory(directory: ManagedFile) {
    this._router.navigate(['.', {dir: JSON.stringify([...this.parentDirs, directory.filename])}]);
  }

  navigateDir(dirIndex: number) {
    this._router.navigate(['.', {dir: JSON.stringify(this.parentDirs.slice(0, dirIndex + 1))}]);
  }

  private loadFiles() {
    this.isRefreshing$.next(true);
    this.files$.next([]);
    this._fileService.findAllFilesInDirectory(this.parentDirs)
      .takeUntil(this.ngUnsubscribe$)
      .finally(() => this.isRefreshing$.next(false))
      .subscribe((results: ManagedFile[]) => {
        this.files$.next(results);
      });
  }
}
