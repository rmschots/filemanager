import { Component, OnInit } from '@angular/core';
import { DataFile } from '../shared/models/data-file';
import { ActivatedRoute, Router } from '@angular/router';
import { Unsubscribable } from '../shared/util/unsubscribable';

@Component({
  selector: 'app-explorer',
  templateUrl: './explorer.component.html',
  styleUrls: ['./explorer.component.css']
})
export class ExplorerComponent extends Unsubscribable implements OnInit {

  parentDirs: string[] = [];
  folders: DataFile[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router) {
    super();
  }

  ngOnInit() {
    console.log('init');
    this.route
      .params
      .takeUntil(this.ngUnsubscribe$)
      .subscribe(params => {
        const dirParams = params['dir'];
        if (dirParams) {
          this.parentDirs = JSON.parse(params['dir']);
        } else {
          this.parentDirs = [];
        }
      });
    this.folders.push(new DataFile('test', new Date(), 'author'));
    this.folders.push(new DataFile('test2', new Date(), 'author2'));
    this.folders.push(new DataFile('test3', new Date(), 'author3'));
  }

  enterDirectory(directory: DataFile) {
    this.router.navigate(['.', { dir: JSON.stringify([...this.parentDirs, directory.name]) }]);
  }

  goUpDirectory() {
    this.router.navigate(['.', { dir: JSON.stringify(this.parentDirs.slice(0, this.parentDirs.length - 1)) }]);
  }
}
