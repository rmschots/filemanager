import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FileService } from './services/file.service';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule, MatProgressBarModule, MatProgressSpinnerModule } from '@angular/material';
import { FileSizePipe } from './pipes/file-size.pipe';
import { DownloadConfirmComponent } from './components/download-confirm/download-confirm.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UploadFileComponent } from './components/upload-file/upload-file.component';
import { LoggedInGuard } from './guards/logged-in.guard';
import { UserService } from './services/user.service';

const MATERIAL_IMPORTS = [
  MatToolbarModule,
  MatButtonModule,
  MatIconModule,
  MatListModule,
  MatProgressSpinnerModule,
  MatDialogModule,
  MatProgressBarModule
];

const MODULES = [
  CommonModule,
  RouterModule,
  BrowserAnimationsModule,
  FormsModule,
  HttpClientModule,
  FlexLayoutModule,
  ...MATERIAL_IMPORTS
];

const MODALS = [
  DownloadConfirmComponent,
  UploadFileComponent
];

const COMPONENTS = [
  NavbarComponent,
  ...MODALS
];

const SERVICES = [
  FileService,
  UserService
];

const PIPES = [
  FileSizePipe
];

const GUARDS = [
  LoggedInGuard
];

@NgModule({
  imports: [
    ...MODULES
  ],
  exports: [
    ...MODULES,
    ...COMPONENTS,
    ...PIPES
  ],
  declarations: [
    ...COMPONENTS,
    ...PIPES
  ],
  entryComponents: [
    MODALS
  ]
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [...SERVICES, ...GUARDS]
    };
  }
}
