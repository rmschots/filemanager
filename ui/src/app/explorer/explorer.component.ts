import { Component, OnInit } from '@angular/core';
import { DataFile } from '../shared/models/data-file';

@Component({
  selector: 'app-explorer',
  templateUrl: './explorer.component.html',
  styleUrls: ['./explorer.component.css']
})
export class ExplorerComponent implements OnInit {

  folders: DataFile[] = [];

  ngOnInit() {
    this.folders.push(new DataFile('test', new Date(), 'author'));
  }

}
