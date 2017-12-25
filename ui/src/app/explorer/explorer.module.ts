import { NgModule } from '@angular/core';
import { ExplorerComponent } from './explorer.component';
import { SharedModule } from '../shared/shared.module';
import { ExplorerRoutingModule } from './explorer-routing.module';

@NgModule({
  imports: [
    SharedModule,
    ExplorerRoutingModule
  ],
  declarations: [ExplorerComponent]
})
export class ExplorerModule {
}
