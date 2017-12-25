import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ExplorerComponent } from './explorer.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: ExplorerComponent
      }
    ])
  ],
  exports: [RouterModule],
  schemas: []
})
export class ExplorerRoutingModule {
}
