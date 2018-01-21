import { ExplorerComponent } from './explorer.component';
import { ActivatedRoute, Router } from '@angular/router';
import { FileService } from '../shared/services/file.service';
import { MatDialog } from '@angular/material';
import { of } from 'rxjs/observable/of';
import { ManagedFile } from '../shared/models/managed-file';
import { Subject } from 'rxjs/Subject';

const managedFileSubject = new Subject();
const managedFile = new ManagedFile('filename', new Date(), new Date(), 2);

class ActivatedRouteMock {
  params = of({dir: '["angular"]'});
}

class RouterMock {
}

class FileServiceMock {
  findAllFilesInDirectory = (dir: string[]) => managedFileSubject;
}

class MatDialogMock {
}

describe('explorer unit tests', () => {
  let explorer: ExplorerComponent = null;
  let activatedRoute: ActivatedRoute;
  let router: Router;
  let fileService: FileService;
  let matDialog: MatDialog;

  beforeEach(() => {
    activatedRoute = new ActivatedRouteMock() as any;
    router = new RouterMock() as any;
    fileService = new FileServiceMock() as any;
    matDialog = new MatDialogMock() as any;

    explorer = new ExplorerComponent(activatedRoute, router, fileService, matDialog);
  });

  it('tests pure functionality', () => {
    explorer.ngOnInit();

    expect(explorer.parentDirs).toEqual(['angular']);
    expect(explorer.files$.getValue()).toEqual([]);
    expect(explorer.isRefreshing$.getValue()).toBe(true);

    // http call returns
    managedFileSubject.next([managedFile]);

    expect(explorer.isRefreshing$.getValue()).toBe(true);

    // http call completed
    managedFileSubject.complete();
    expect(explorer.isRefreshing$.getValue()).toBe(false);
    expect(explorer.files$.getValue()).toContain(managedFile);
  });
});
