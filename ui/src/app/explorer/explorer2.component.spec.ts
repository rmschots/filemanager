import { ExplorerComponent } from './explorer.component';
import { of } from 'rxjs/observable/of';
import { ManagedFile } from '../shared/models/managed-file';
import { Subject } from 'rxjs/Subject';
import { async, ComponentFixture, TestBed, tick } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FileService } from '../shared/services/file.service';
import { MatDialog, MatProgressSpinnerModule } from '@angular/material';
import { By } from '@angular/platform-browser';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FileSizePipe } from '../shared/pipes/file-size.pipe';
import { MatListModule } from '@angular/material/list';

const managedFileSubject = new Subject();
const managedFile = new ManagedFile('filename', new Date(), new Date(), 2);
const managedFile2 = new ManagedFile('filename2', new Date(), new Date(), 3);

const activatedRoute = {
  params: of({dir: '["angular"]'})
};

const router = {};

const fileService = {
  findAllFilesInDirectory: (dir: string[]) => managedFileSubject
};

const matDialog = {};

describe('explorer component tests', () => {
  let explorer: ExplorerComponent;
  let fixture: ComponentFixture<ExplorerComponent>;
  let de: DebugElement;
  let el: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ExplorerComponent, FileSizePipe], // declare the test component
      imports: [MatIconModule, MatProgressSpinnerModule, MatToolbarModule, MatListModule],
      providers: [
        {provide: ActivatedRoute, useValue: activatedRoute},
        {provide: Router, useValue: router},
        {provide: FileService, useValue: fileService},
        {provide: MatDialog, useValue: matDialog}
      ]
    }).compileComponents();
  }));
  beforeEach(() => {
    fixture = TestBed.createComponent(ExplorerComponent);

    explorer = fixture.componentInstance; // BannerComponent test instance
    de = fixture.debugElement;
    el = de.nativeElement;
  });

  it('tests the component initialization', () => {
    expect(de.query(By.css('[aria-label="Home"]'))).toBeDefined();

    fixture.detectChanges();

    expect(explorer.parentDirs).toEqual(['angular']);
    expect(explorer.files$.getValue()).toEqual([]);
    expect(explorer.isRefreshing$.getValue()).toBe(true);

    // http call returns
    managedFileSubject.next([managedFile, managedFile2]);

    expect(explorer.isRefreshing$.getValue()).toBe(true);

    // http call completed
    managedFileSubject.complete();
    fixture.detectChanges();

    expect(explorer.isRefreshing$.getValue()).toBe(false);
    expect(explorer.files$.getValue()).toContain(managedFile, managedFile2);
    expect(
      de.queryAll(By.css('mat-list-item span:first-of-type'))
        .map(el => el.nativeElement.textContent.trim())
    ).toEqual([managedFile.filename, managedFile2.filename]);
  });
});
