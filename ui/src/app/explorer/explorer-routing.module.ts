import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoggedInGuard } from '../shared/guards/logged-in.guard';
import { ExplorerComponent } from './explorer.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'explorer',
        component: ExplorerComponent,
        canActivate: [LoggedInGuard]
      }
    ])
  ],
  exports: [RouterModule],
  schemas: []
})
export class ExplorerRoutingModule {
}
