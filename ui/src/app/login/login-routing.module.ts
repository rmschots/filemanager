import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'login',
        component: LoginComponent
      }
    ])
  ],
  exports: [RouterModule],
  schemas: []
})
export class LoginRoutingModule {
}
